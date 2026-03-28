package com.ashutosh.wordcount.rules;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LengthGreaterThanFiveRule")
class LengthGreaterThanFiveRuleTest {

  private LengthGreaterThanFiveRule rule;

  @BeforeEach
  void setUp() {
    rule = new LengthGreaterThanFiveRule();
  }

  @Test
  @DisplayName("returns words longer than five characters")
  void returnsWordsLongerThanFive() {
    final List<String> words = List.of("Strawberry", "Mango", "blueberry");
    final List<String> result = rule.apply(words);
    assertEquals(List.of("Strawberry", "blueberry"), result);
  }

  @Test
  @DisplayName("excludes words with exactly five characters")
  void excludesExactlyFiveCharacters() {
    final List<String> words = List.of("Mango", "Peach", "apple");
    // all exactly 5 — none returned
    assertEquals(List.of(), rule.apply(words));
  }

  @Test
  @DisplayName("includes words with exactly six characters")
  void includesExactlySixCharacters() {
    final List<String> words = List.of("Mangoo"); // 6 chars
    assertEquals(List.of("Mangoo"), rule.apply(words));
  }

  @Test
  @DisplayName("returns empty list when no words are longer than five")
  void returnsEmptyListWhenNoMatch() {
    final List<String> words = List.of("kiwi", "fig", "Peach");
    assertEquals(List.of(), rule.apply(words));
  }

  @Test
  @DisplayName("returns empty list for empty input")
  void returnsEmptyListForEmptyInput() {
    assertEquals(List.of(), rule.apply(List.of()));
  }

  @Test
  @DisplayName("preserves order of words from input")
  void preservesInputOrder() {
    final List<String> words = List.of("blueberry", "Strawberry", "Mandarin");
    final List<String> result = rule.apply(words);
    assertEquals(List.of("blueberry", "Strawberry", "Mandarin"), result);
  }

  @Test
  @DisplayName("returned list is unmodifiable")
  void returnedListIsUnmodifiable() {
    final List<String> words = List.of("Strawberry");
    final List<String> result = rule.apply(words);
    assertThrows(UnsupportedOperationException.class, () -> result.add("extra"));
  }

  @Test
  @DisplayName("name returns simple class name")
  void nameReturnsSimpleClassName() {
    assertEquals("LengthGreaterThanFiveRule", rule.name());
  }
}
