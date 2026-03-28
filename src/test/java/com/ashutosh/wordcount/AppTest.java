package com.ashutosh.wordcount;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("App")
class AppTest {

  @Test
  @DisplayName("main runs without throwing any exception")
  void mainRunsWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }
}
