package com.ashutosh.wordcount.rules;

import java.util.List;

/**
 * Word rule that counts words starting with 'M' or 'm'.
 *
 * <p>The comparison is case-sensitive to the first character only — both uppercase 'M' and
 * lowercase 'm' are matched.
 */
public class StartsWithMRule implements WordRule<Long> {

  /** Creates a new StartsWithMRule instance. */
  public StartsWithMRule() {}

  /**
   * Counts words in the given list that start with 'M' or 'm'.
   *
   * @param words the list of words to evaluate — must not be null
   * @return the count of matching words as a {@code Long}
   */
  @Override
  public Long apply(final List<String> words) {
    return words.stream().filter(w -> w.startsWith("M") || w.startsWith("m")).count();
  }
}
