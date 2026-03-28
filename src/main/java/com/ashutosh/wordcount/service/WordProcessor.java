package com.ashutosh.wordcount.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ashutosh.wordcount.model.Result;
import com.ashutosh.wordcount.rules.WordRule;

/**
 * Executes a pipeline of word rules against a given list of words. Each rule is
 * applied independently and results are collected into a Result.
 */
public class WordProcessor {
	/** Creates a new WordProcessor instance. */
	public WordProcessor() {
	}

	private static final Logger log = LoggerFactory.getLogger(WordProcessor.class);

	/**
	 * Processes the word list by applying all provided rules in order.
	 *
	 * @param words the list of words to process
	 * @param rules the rules to apply as a pipeline
	 * @return a Result containing the output of each rule keyed by rule name
	 */
	public Result process(final List<String> words, final List<WordRule<?>> rules) {

		// guard clauses — fail fast before any processing begins
		Objects.requireNonNull(words, "Word list must not be null");
		Objects.requireNonNull(rules, "Rules list must not be null");

		// reject null words inside the list
		if (words.stream().anyMatch(Objects::isNull)) {
			throw new IllegalArgumentException("Word list must not contain null elements");
		}

		// reject null rules inside the list
		if (rules.stream().anyMatch(Objects::isNull)) {
			throw new IllegalArgumentException("Rules list must not contain null elements");
		}

		final List<String> immutableWords = List.copyOf(words);
		log.info("Starting processing: {} words, {} rules", immutableWords.size(), rules.size());

		final Map<String, Object> outputs = new LinkedHashMap<>();

		rules.forEach(rule -> {
			log.debug("Applying rule: {}", rule.name());

			final Object rawOutput = rule.apply(immutableWords);

			final Object output = Optional.ofNullable(rawOutput).orElseGet(() -> {
				log.warn("Rule [{}] returned null — storing empty result", rule.name());
				return Collections.emptyList();
			});

			outputs.put(rule.name(), output);
			log.debug("Rule [{}] produced: {}", rule.name(), output);
		});

		log.info("Processing complete. Rules applied: {}", outputs.keySet());

		return new Result(Collections.unmodifiableMap(outputs));
	}
}