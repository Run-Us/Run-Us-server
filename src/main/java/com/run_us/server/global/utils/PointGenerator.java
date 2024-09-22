package com.run_us.server.global.utils;

import com.run_us.server.domains.running.domain.LocationData;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class PointGenerator {

  private static final GeometryFactory geometryFactory = new GeometryFactory();

  public static Point generatePoint(Long latitude, Long longitude) {
    return geometryFactory.createPoint(new Coordinate(latitude, longitude));
  }

  public static Point generatePoint(LocationData.Point point) {
    return geometryFactory.createPoint(new Coordinate(point.getLatitude(), point.getLongitude()));
  }
}
