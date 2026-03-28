package com.ashutosh.wordcount.rules;

/**
 * Enumeration of all available word rule types.
 *
 * <p>Add a new constant here when a new business rule is introduced,
 * then register it in {@link WordRuleFactory}.
 */
public enum RuleType {

    /** Matches words that start with the letter 'M' or 'm'. */
    STARTS_WITH_M,

    /** Matches words whose length is greater than five characters. */
    LENGTH_GREATER_THAN_FIVE
}