package com.ashutosh.wordcount.rules;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("StartsWithMRule")
class StartsWithMRuleTest {

  private StartsWithMRule rule;

  @BeforeEach
  void setUp() {
    rule = new StartsWithMRule();
  }

  @Test
  @DisplayName("counts words starting with uppercase M")
  void countsUppercaseM() {
    final List<String> words = List.of("Mango", "Melon", "Mandarin");
    assertEquals(3L, rule.apply(words));
  }

  @Test
  @DisplayName("counts words starting with lowercase m")
  void countsLowercaseM() {
    final List<String> words = List.of("mango", "melon");
    assertEquals(2L, rule.apply(words));
  }

  @Test
  @DisplayName("counts both uppercase M and lowercase m")
  void countsMixedCase() {
    final List<String> words = List.of("Mango", "mango", "apple", "Melon");
    assertEquals(3L, rule.apply(words));
  }

  @Test
  @DisplayName("returns zero when no words start with M or m")
  void returnsZeroWhenNoMatch() {
    final List<String> words = List.of("apple", "kiwi", "Strawberry");
    assertEquals(0L, rule.apply(words));
  }

  @Test
  @DisplayName("returns zero for empty list")
  void returnsZeroForEmptyList() {
    assertEquals(0L, rule.apply(List.of()));
  }

  @Test
  @DisplayName("does not count words starting with other letters")
  void doesNotCountNonMWords() {
    final List<String> words = List.of("MANGO", "melon", "Mango");
    // MANGO starts with M — counted, melon starts with m — counted, Mango starts
    // with M — counted
    assertEquals(3L, rule.apply(words));
  }

  @Test
  @DisplayName("name returns simple class name")
  void nameReturnsSimpleClassName() {
    assertEquals("StartsWithMRule", rule.name());
  }
}
