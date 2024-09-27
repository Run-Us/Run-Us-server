package com.run_us.server.domains.running.repository;

import com.run_us.server.domains.running.domain.LocationData.RunnerPos;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/***
 * In-Memory Data Storage which maintains live location logs of runners.
 * Key-value pair is used to store the location logs.
 * Key: Running ID
 * Value: List of RunnerPos, list is append-only.
 */


@Repository
public class InMemoryLocationLogs implements UpdateLocationRepository {

  private static final int INIT_SIZE = 50;
  private final ConcurrentHashMap<String, Deque<RunnerPos>> locationLogs = new ConcurrentHashMap<>();


  @Override
  public void saveLocation(String key, RunnerPos runnerPos) {
    locationLogs.computeIfAbsent(key, k -> new ArrayDeque<>(INIT_SIZE));
    locationLogs.get(key).add(runnerPos);
  }

  @Override
  public Optional<List<RunnerPos>> getLocationLogs(String key) {
    if(locationLogs.containsKey(key)) {
      return Optional.of(List.copyOf(locationLogs.get(key)));
    }
    return Optional.empty();
  }

  @Override
  public void removeLocationLogs(String key) {
    locationLogs.remove(key);
  }
}
