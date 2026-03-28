package com.ashutosh.wordcount;

import com.ashutosh.wordcount.model.Result;
import com.ashutosh.wordcount.rules.RuleType;
import com.ashutosh.wordcount.rules.WordRule;
import com.ashutosh.wordcount.rules.WordRuleFactory;
import com.ashutosh.wordcount.service.WordProcessor;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Hello world! */
/**
 * Entry point for the Count Words application. Demonstrates pluggable rule execution via the
 * WordProcessor pipeline.
 */
public class App {

  /** Utility class — not instantiable. */
  private App() {}

  private static final Logger log = LoggerFactory.getLogger(App.class);

  /**
   * Application entry point.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(final String[] args) {

    // input list — immutable at the source, no standard input required
    final List<String> words =
        List.of(
            "Mango",
            "apple",
            "Melon",
            "kiwi",
            "Strawberry",
            "mango",
            "fig",
            "Mandarin",
            "blueberry",
            "Peach");

    log.info("Input words: {}", words);

    // select rules via Factory — no direct instantiation in App
    final List<WordRule<?>> rules =
        List.of(
            WordRuleFactory.get(RuleType.STARTS_WITH_M),
            WordRuleFactory.get(RuleType.LENGTH_GREATER_THAN_FIVE));

    // execute pipeline
    final WordProcessor processor = new WordProcessor();
    final Result result = processor.process(words, rules);

    // print results using Optional-safe getOutput()
    result
        .getOutput("StartsWithMRule")
        .ifPresent(count -> System.out.println("Words starting with M/m: " + count));

    result
        .getOutput("LengthGreaterThanFiveRule")
        .ifPresent(list -> System.out.println("Words longer than 5 characters: " + list));

    log.info("Application complete. Full result: {}", result);
  }
}
