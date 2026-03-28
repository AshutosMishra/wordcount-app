package com.ashutosh.wordcount.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Result")
class ResultTest {

  @Test
  @DisplayName("getOutputs returns the full map")
  void getOutputsReturnsFullMap() {
    final Map<String, Object> map = Map.of("StartsWithMRule", 3L);
    final Result result = new Result(map);
    assertEquals(map, result.getOutputs());
  }

  @Test
  @DisplayName("getOutput returns correct value for known rule name")
  void getOutputReturnsValueForKnownKey() {
    final Map<String, Object> map = Map.of("StartsWithMRule", 3L);
    final Result result = new Result(map);
    final Optional<Object> output = result.getOutput("StartsWithMRule");
    assertTrue(output.isPresent());
    assertEquals(3L, output.get());
  }

  @Test
  @DisplayName("getOutput returns Optional.empty for unknown rule name")
  void getOutputReturnsEmptyForUnknownKey() {
    final Result result = new Result(Map.of());
    assertEquals(Optional.empty(), result.getOutput("UnknownRule"));
  }

  @Test
  @DisplayName("constructor throws NullPointerException for null map")
  void constructorThrowsOnNullMap() {
    final NullPointerException ex =
        assertThrows(NullPointerException.class, () -> new Result(null));
    assertEquals("Outputs map must not be null", ex.getMessage());
  }

  @Test
  @DisplayName("getOutput throws NullPointerException for null rule name")
  void getOutputThrowsOnNullRuleName() {
    final Result result = new Result(Map.of());
    assertThrows(NullPointerException.class, () -> result.getOutput(null));
  }

  @Test
  @DisplayName("toString includes rule outputs")
  void toStringIncludesOutputs() {
    final Result result = new Result(Map.of("StartsWithMRule", 2L));
    assertTrue(result.toString().contains("StartsWithMRule"));
  }
}
