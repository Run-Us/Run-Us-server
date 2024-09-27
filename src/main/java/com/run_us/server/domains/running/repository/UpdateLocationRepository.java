package com.run_us.server.domains.running.repository;

import com.run_us.server.domains.running.domain.LocationData.RunnerPos;
import java.util.List;
import java.util.Optional;

public interface UpdateLocationRepository {

  void saveLocation(String key, RunnerPos runnerPos);

  Optional<List<RunnerPos>> getLocationLogs(String key);

  void removeLocationLogs(String key);
}
