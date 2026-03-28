package com.ashutosh.wordcount.rules;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Word rule that returns all words longer than five characters.
 *
 * <p>Words with exactly five characters are excluded. The order of words in the returned list
 * matches the order in the input.
 */
public class LengthGreaterThanFiveRule implements WordRule<List<String>> {

  /** Creates a new LengthGreaterThanFiveRule instance. */
  public LengthGreaterThanFiveRule() {}

  /**
   * Filters the given list and returns only words longer than five characters.
   *
   * @param words the list of words to evaluate — must not be null
   * @return an unmodifiable list of words whose length is greater than five
   */
  @Override
  public List<String> apply(final List<String> words) {
    return words.stream().filter(w -> w.length() > 5).collect(Collectors.toUnmodifiableList());
  }
}
