package com.ashutosh.wordcount.service;

import com.ashutosh.wordcount.model.Result;
import com.ashutosh.wordcount.rules.LengthGreaterThanFiveRule;
import com.ashutosh.wordcount.rules.StartsWithMRule;
import com.ashutosh.wordcount.rules.WordRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WordProcessor")
class WordProcessorTest {

    private WordProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new WordProcessor();
    }

    @Test
    @DisplayName("applies StartsWithMRule and returns correct count")
    void appliesStartsWithMRule() {
        final List<String> words = List.of("Mango", "apple", "mango");
        final Result result = processor.process(words, List.of(new StartsWithMRule()));
        assertEquals(2L, result.getOutput("StartsWithMRule").orElseThrow());
    }

    @Test
    @DisplayName("applies LengthGreaterThanFiveRule and returns correct words")
    void appliesLengthGreaterThanFiveRule() {
        final List<String> words = List.of("Strawberry", "kiwi", "blueberry");
        final Result result = processor.process(words, List.of(new LengthGreaterThanFiveRule()));
        assertEquals(List.of("Strawberry", "blueberry"),
                result.getOutput("LengthGreaterThanFiveRule").orElseThrow());
    }

    @Test
    @DisplayName("applies both rules and both results present in Result")
    void appliesBothRules() {
        final List<String> words = List.of("Mango", "Strawberry", "mango", "fig");
        final List<WordRule<?>> rules = List.of(
                new StartsWithMRule(),
                new LengthGreaterThanFiveRule()
        );
        final Result result = processor.process(words, rules);
        assertTrue(result.getOutput("StartsWithMRule").isPresent());
        assertTrue(result.getOutput("LengthGreaterThanFiveRule").isPresent());
        assertEquals(2L, result.getOutput("StartsWithMRule").orElseThrow());
        assertEquals(List.of("Strawberry"), result.getOutput("LengthGreaterThanFiveRule").orElseThrow());
    }

    @Test
    @DisplayName("empty rules list returns Result with empty outputs")
    void emptyRulesReturnsEmptyResult() {
        final List<String> words = List.of("Mango", "apple");
        final Result result = processor.process(words, List.of());
        assertTrue(result.getOutputs().isEmpty());
    }

    @Test
    @DisplayName("empty words list returns zero count and empty filtered list")
    void emptyWordsListReturnsValidResult() {
        final List<WordRule<?>> rules = List.of(
                new StartsWithMRule(),
                new LengthGreaterThanFiveRule()
        );
        final Result result = processor.process(List.of(), rules);
        assertEquals(0L, result.getOutput("StartsWithMRule").orElseThrow());
        assertEquals(List.of(), result.getOutput("LengthGreaterThanFiveRule").orElseThrow());
    }

    @Test
    @DisplayName("throws NullPointerException when words list is null")
    void throwsOnNullWordsList() {
        final NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> processor.process(null, List.of())
        );
        assertEquals("Word list must not be null", ex.getMessage());
    }

    @Test
    @DisplayName("throws NullPointerException when rules list is null")
    void throwsOnNullRulesList() {
        assertThrows(
                NullPointerException.class,
                () -> processor.process(List.of("Mango"), null)
        );
    }

    @Test
    @DisplayName("throws IllegalArgumentException when words list contains null element")
    void throwsOnNullElementInWordsList() {
        final List<String> words = new java.util.ArrayList<>();
        words.add("Mango");
        words.add(null);
        assertThrows(
                IllegalArgumentException.class,
                () -> processor.process(words, List.of())
        );
    }

    @Test
    @DisplayName("throws IllegalArgumentException when rules list contains null element")
    void throwsOnNullElementInRulesList() {
        final List<WordRule<?>> rules = new java.util.ArrayList<>();
        rules.add(new StartsWithMRule());
        rules.add(null);
        assertThrows(
                IllegalArgumentException.class,
                () -> processor.process(List.of("Mango"), rules)
        );
    }
}