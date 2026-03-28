package com.ashutosh.wordcount.rules;

import java.util.List;

/**
 * Functional interface representing a single word rule.
 *
 * <p>Each rule takes a list of words and returns a result of type {@code T}.
 * Rules are designed to be stateless and reusable.
 *
 * @param <T> the return type of the rule (e.g. {@code Long} for a count,
 *            {@code List<String>} for filtered words)
 */
@FunctionalInterface
public interface WordRule<T> {

    /**
     * Applies this rule to the given list of words.
     *
     * @param words the list of words to evaluate — must not be null
     * @return the result of applying this rule
     */
    T apply(List<String> words);

    /**
     * Returns the name of this rule, used as a key in the {@link com.ashutosh.wordcount.model.Result} map.
     * Defaults to the simple class name of the implementing class.
     *
     * @return the rule name
     */
    default String name() {
        return getClass().getSimpleName();
    }
}