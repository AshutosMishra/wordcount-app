package com.ashutosh.wordcount.model;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Immutable container holding the output of all applied word rules. */
public final class Result {

  private final Map<String, Object> outputs;

  /**
   * Constructs a Result with the given rule outputs.
   *
   * @param outputs map of rule name to rule output — must not be null
   */
  public Result(final Map<String, Object> outputs) {
    this.outputs = Objects.requireNonNull(outputs, "Outputs map must not be null");
  }

  /**
   * Returns all rule outputs.
   *
   * @return unmodifiable map of rule name to output
   */
  public Map<String, Object> getOutputs() {
    return outputs;
  }

  /**
   * Returns the output for a specific rule name if present.
   *
   * @param ruleName the name of the rule — must not be null
   * @return Optional containing the result, or empty if not found
   */
  public Optional<Object> getOutput(final String ruleName) {
    Objects.requireNonNull(ruleName, "Rule name must not be null");
    return Optional.ofNullable(outputs.get(ruleName));
  }

  @Override
  public String toString() {
    return "Result" + outputs;
  }
}
