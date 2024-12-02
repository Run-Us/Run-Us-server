package com.run_us.server.domains.running.live.repository;

import com.run_us.server.domains.running.live.service.model.LocationData;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/***
 * 달리는 사용자의 실시간 위치 업데이트로그를 저장하는 저장소.
 * key-value 구조로 저장되며, key는 러닝ID와 유저ID로 생성된다.
 * value는 위치정보를 append-only로 저장하는 Deque이다.
 */
@Repository
public class InMemoryLocationLogs implements UpdateLocationRepository {

  private static final int INIT_SIZE = 50;
  private final ConcurrentHashMap<String, Deque<LocationData.RunnerPos>> locationLogs =
      new ConcurrentHashMap<>();

  /***
   * 유저정보와 러닝정보로 생성된 키에 위치정보를 저장. 위치정보는 append-only로 저장된다.
   * @param key 러닝정보와 유저정보로 생성된 키
   * @param runnerPos 위치정보
   */
  @Override
  public void saveLocation(String key, LocationData.RunnerPos runnerPos) {
    locationLogs.computeIfAbsent(key, k -> new ArrayDeque<>(INIT_SIZE));
    locationLogs.get(key).add(runnerPos);
  }

  /***
   * 키에 해당하는 위치정보 리스트를 반환한다. 일치하는 키가 없으면 Optional.empty()를 반환한다.
   * @param key 러닝정보와 유저정보로 생성된 키
   * @return Optional<List<RunnerPos>>
   */
  @Override
  public Optional<List<LocationData.RunnerPos>> getLocationLogs(String key) {
    if (locationLogs.containsKey(key)) {
      return Optional.of(List.copyOf(locationLogs.get(key)));
    }
    return Optional.empty();
  }

  /***
   * 키에 해당하는 키-위치정보리스트 쌍을 삭제한다.
   * @param key 러닝정보와 유저정보로 생성된 키
   * @throws NoSuchElementException 일치하는 키가 없을 때
   */
  @Override
  public void removeLocationLogs(String key) {
    if (!locationLogs.containsKey(key)) throw new NoSuchElementException();
    locationLogs.remove(key);
  }
}
