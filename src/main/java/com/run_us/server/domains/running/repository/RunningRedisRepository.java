package com.run_us.server.domains.running.repository;

import static com.run_us.server.domains.running.service.util.RunningKeyUtil.createLiveKey;
import static com.run_us.server.domains.running.service.util.RunningKeyUtil.extractUserIdFromKey;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.run_us.server.domains.running.domain.LocationData;
import com.run_us.server.domains.running.domain.LocationData.RunnerPos;
import com.run_us.server.domains.running.domain.ParticipantStatus;
import com.run_us.server.domains.running.domain.RunningConst;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RunningRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RunningRedisRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /***
     * 러닝세션 참가자의 상태를 업데이트
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     * @param status 참가자의 상태(enum: RUN, PAUSE, END)
     */
    public void updateParticipantStatus(String runningId, String userId, ParticipantStatus status) {
        String key = createLiveKey(runningId, userId, RunningConst.STATUS_SUFFIX);
        redisTemplate.opsForValue().set(key, status.name());
    }

    /***
     * 러닝세션 참가자의 상태를 조회
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     * @return 질의한 유저의 상태(enum: RUN, PAUSE, END)
     */
    public ParticipantStatus getParticipantStatus(String runningId, String userId) {
        String key = createLiveKey(runningId, userId, RunningConst.STATUS_SUFFIX);
        String status = redisTemplate.opsForValue().get(key);
        return status != null ? ParticipantStatus.valueOf(status) : null;
    }

    /***
     * 러닝세션 참가자의 위치정보를 업데이트
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     * @param latitude 위도
     * @param longitude 경도
     * @param count 송신 횟수
     */
    public void updateParticipantLocation(String runningId, String userId, double latitude, double longitude, long count) {
        String key = createLiveKey(runningId, userId, RunningConst.LOCATION_SUFFIX);
        String currentValue = redisTemplate.opsForValue().get(key);

        try {
            if (currentValue != null) {
                LocationData current = objectMapper.readValue(currentValue, LocationData.class);
                if (count <= current.getCount()) {
                    return; // 기존 정보보다 과거 정보라면 업데이트 하지 않고 폐기
                }
            }

            LocationData newLocation = new LocationData(latitude, longitude, count);
            String newValue = objectMapper.writeValueAsString(newLocation);
            redisTemplate.opsForValue().set(key, newValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update location", e);
        }
    }

    /***
     * 러닝세션 참가자의 마지막(최신) 위치정보를 조회
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     * @return 마지막(최신) 위치정보
     */
    public RunnerPos getParticipantLocation(String runningId, String userId) {
        String key = createLiveKey(runningId, userId, RunningConst.LOCATION_SUFFIX);
        String value = redisTemplate.opsForValue().get(key);
        try {
            if (value != null) {
                LocationData locationData = objectMapper.readValue(value, LocationData.class);
                return new RunnerPos(locationData.getLatitude(), locationData.getLongitude());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get location", e);
        }
        return null;
    }

    /***
     * 러닝세션 제거
     * @param runningId 러닝세션 외부 노출용 ID
     */
    public void deleteRunningSession(String runningId) {
        String pattern = createLiveKey(runningId, "*", "");
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /***
     * 러닝세션 참가자 목록 조회
     * @param runningId 러닝세션 외부 노출용 ID
     * @return 러닝세션 참가자의 외부 노출용 ID 목록
     */
    public Set<String> getSessionParticipants(String runningId) {
        String pattern = createLiveKey(runningId, "*", RunningConst.STATUS_SUFFIX);
        Set<String> keys = redisTemplate.keys(pattern);
        Set<String> participants = new HashSet<>();
        if (keys != null) {
            for (String key : keys) {
                String userId = extractUserIdFromKey(key);
                participants.add(userId);
            }
        }
        return participants;
    }

    /***
     * 러닝세션 참가자(전체)의 위치정보 조회
     * @param runningId 러닝세션 외부 노출용 ID
     * @return 러닝세션 참가자 전체의 위치정보 목록
     */
    private Map<String, RunnerPos> getAllParticipantsLocations(String runningId) {
        Map<String, RunnerPos> participantsLocations = new HashMap<>();
        String pattern = createLiveKey(runningId, "*", RunningConst.LOCATION_SUFFIX);
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys != null) {
            for (String key : keys) {
                String userId = extractUserIdFromKey(key);
                String locationJson = redisTemplate.opsForValue().get(key);
                try {
                    LocationData locationData = objectMapper.readValue(locationJson, LocationData.class);
                    participantsLocations.put(userId,
                            new RunnerPos(locationData.getLatitude(), locationData.getLongitude()));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to read location data", e);
                }
            }
        }

        return participantsLocations;
    }

    /***
     * 러닝세션 참가자(한명)의 위치정보 업데이트를 publish
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     * @param latitude 위도
     * @param longitude 경도
     */
    public void publishLocationUpdateSingle(String runningId, String userId, double latitude, double longitude) {
        String channel = "location_updates:" + runningId;
        Map<String, RunnerPos> locationUpdate = new HashMap<>();
        locationUpdate.put(userId, new RunnerPos(latitude, longitude));

        try {
            String message = objectMapper.writeValueAsString(locationUpdate);
            redisTemplate.convertAndSend(channel, message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish single location update", e);
        }
    }

    /***
     * 러닝세션 참가자(전체)의 위치정보 업데이트를 publish
     * @param runningId 러닝세션 외부 노출용 ID
     */
    public void publishLocationUpdatesAll(String runningId) {
        String channel = "location_updates:" + runningId;
        Map<String, RunnerPos> participantsLocations = getAllParticipantsLocations(runningId);
        try {
            String message = objectMapper.writeValueAsString(participantsLocations);
            redisTemplate.convertAndSend(channel, message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish location updates", e);
        }
    }


}