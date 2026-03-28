package com.ashutosh.wordcount.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WordRuleFactory")
class WordRuleFactoryTest {

  @Test
  @DisplayName("returns StartsWithMRule for STARTS_WITH_M")
  void returnsStartsWithMRule() {
    final WordRule<?> rule = WordRuleFactory.get(RuleType.STARTS_WITH_M);
    assertInstanceOf(StartsWithMRule.class, rule);
  }

  @Test
  @DisplayName("returns LengthGreaterThanFiveRule for LENGTH_GREATER_THAN_FIVE")
  void returnsLengthGreaterThanFiveRule() {
    final WordRule<?> rule = WordRuleFactory.get(RuleType.LENGTH_GREATER_THAN_FIVE);
    assertInstanceOf(LengthGreaterThanFiveRule.class, rule);
  }

  @Test
  @DisplayName("returns cached instance — same object on repeated calls")
  void returnsCachedInstance() {
    final WordRule<?> first = WordRuleFactory.get(RuleType.STARTS_WITH_M);
    final WordRule<?> second = WordRuleFactory.get(RuleType.STARTS_WITH_M);
    assertSame(first, second);
  }

  @Test
  @DisplayName("throws NullPointerException for null RuleType")
  void throwsOnNullRuleType() {
    final NullPointerException ex =
        assertThrows(NullPointerException.class, () -> WordRuleFactory.get(null));
    assertEquals("RuleType must not be null", ex.getMessage());
  }
}
