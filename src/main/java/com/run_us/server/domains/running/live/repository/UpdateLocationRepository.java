package com.run_us.server.domains.running.live.repository;

import com.run_us.server.domains.running.live.service.model.LocationData;
import java.util.List;
import java.util.Optional;

public interface UpdateLocationRepository {

  void saveLocation(String key, LocationData.RunnerPos runnerPos);

  Optional<List<LocationData.RunnerPos>> getLocationLogs(String key);

  void removeLocationLogs(String key);
}
