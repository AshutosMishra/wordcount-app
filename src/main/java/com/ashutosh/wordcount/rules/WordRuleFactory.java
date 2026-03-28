package com.ashutosh.wordcount.rules;

import java.util.Map;
import java.util.Objects;

/**
 * Factory that maps a RuleType to its corresponding cached WordRule implementation. Rules are
 * stateless singletons — safe to share across calls.
 */
public class WordRuleFactory {

  // private constructor — no one should instantiate a Factory
  private WordRuleFactory() {}

  /**
   * Returns the WordRule implementation for the given RuleType.
   *
   * @param type the rule type to retrieve
   * @return the corresponding WordRule instance
   * @throws IllegalArgumentException if the RuleType is not mapped
   */
  private static final Map<RuleType, WordRule<?>> CACHE =
      Map.of(
          RuleType.STARTS_WITH_M,
          new StartsWithMRule(),
          RuleType.LENGTH_GREATER_THAN_FIVE,
          new LengthGreaterThanFiveRule());

  /**
   * Returns the cached WordRule implementation for the given RuleType.
   *
   * @param type the rule type to retrieve — must not be null
   * @return the corresponding WordRule instance
   * @throws NullPointerException if type is null
   */
  public static WordRule<?> get(RuleType type) {
    Objects.requireNonNull(type, "RuleType must not be null");
    return switch (type) {
      case STARTS_WITH_M -> CACHE.get(RuleType.STARTS_WITH_M);
      case LENGTH_GREATER_THAN_FIVE -> CACHE.get(RuleType.LENGTH_GREATER_THAN_FIVE);
    };
  }
}
