package com.run_us.server.domains.running.repository;

import static com.run_us.server.domains.running.service.util.RunningKeyUtil.createLiveKey;
import static org.junit.jupiter.api.Assertions.*;

import com.run_us.server.domains.running.domain.LocationData.RunnerPos;
import com.run_us.server.domains.running.domain.RunningConstants;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("InMemoryLocationLogs 클래스의")
class InMemoryLocationLogsTest {

  private InMemoryLocationLogs inMemoryLocationLogs = new InMemoryLocationLogs();

  @Nested
  @DisplayName("addLocation 메소드는")
  class Describe_addLocation {

    @Nested
    @DisplayName("새로운 key와 RunnerPos를 받았을 때")
    class Context_with_a_new_key_and_RunnerPos {

      //given
      private String key = createLiveKey("runningId", "userId", RunningConstants.LOCATION_SUFFIX);
      private RunnerPos runnerPos = RunnerPos.of(1.0, 1.0);

      @AfterEach
      void tearDown() {
        inMemoryLocationLogs.removeLocationLogs(key);
      }

      @Test
      @DisplayName("locationLogs에 key:List<RunnerPos>를 추가한다")
      void it_adds_RunnerPos_to_locationLogs() {

        assertTrue(inMemoryLocationLogs.getLocationLogs(key).isEmpty());
        // When
        inMemoryLocationLogs.saveLocation(key, runnerPos);
        // Then
        assertTrue(inMemoryLocationLogs.getLocationLogs(key).isPresent());
      }
    }

    @Nested
    @DisplayName("이미 존재하는 key와 RunnerPos를 받았을 때")
    class Context_with_an_existing_key_and_RunnerPos {

      //given
      private String key = createLiveKey("runningId", "userId", RunningConstants.LOCATION_SUFFIX);
      private RunnerPos runnerPos = RunnerPos.of(1.0, 1.0);

      @BeforeEach()
      void setup() {
        inMemoryLocationLogs.saveLocation(key, runnerPos);
      }

      @AfterEach
      void tearDown() {
        inMemoryLocationLogs.removeLocationLogs(key);
      }

      @Test
      @DisplayName("locationLogs의 key에 매핑된 리스트에 RunnerPos를 추가한다")
      void it_adds_RunnerPos_to_locationLogs() {

        assertTrue(inMemoryLocationLogs.getLocationLogs(key).isPresent());
        // Given
        inMemoryLocationLogs.saveLocation(key, runnerPos);
        // When
        inMemoryLocationLogs.saveLocation(key, runnerPos);
        // Then
        assertEquals(3, inMemoryLocationLogs.getLocationLogs(key).get().size());
      }
    }
  }

  @Nested
  @DisplayName("removeLocationLogs 메소드는")
  class Describe_removeLocationLogs {

    @Nested
    @DisplayName("저장된 key를 받았을 때")
    class Context_with_an_existing_key {
      //given
      private String key = createLiveKey("runningId", "userId", RunningConstants.LOCATION_SUFFIX);
      private RunnerPos runnerPos = RunnerPos.of(1.0, 1.0);

      @BeforeEach
      void setup() {
        inMemoryLocationLogs.saveLocation(key, runnerPos);
      }

      @Test
      @DisplayName("key와 매핑된 list를 삭제한다.")
      void it_removes_a_key_and_mapped_list() {
        // When
        inMemoryLocationLogs.removeLocationLogs(key);
        // Then
        assertFalse(inMemoryLocationLogs.getLocationLogs(key).isPresent());
      }
    }

    @Nested
    @DisplayName("저장되지 않은 key를 받았을 때")
    class Context_with_a_non_existing_key {

      //given
      private String key = "nonExistingKey";

      @Test
      @DisplayName("NoSuchElementException을 던진다.")
      void it_throws_NoSuchElementException() {
        // When & Then
        assertThrows(NoSuchElementException.class,
            () -> inMemoryLocationLogs.removeLocationLogs(key));
      }
    }
  }
}