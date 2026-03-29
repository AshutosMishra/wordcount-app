# Count Words App

A production-quality Java application that processes a list of words and applies configurable business rules. Built with the **Strategy Pattern**, **Factory Pattern**, and **Pipeline** architecture.

---

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Business Rules](#business-rules)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Generating Javadoc](#generating-javadoc)
- [Design Decisions](#design-decisions)

---

## Overview

Given a predefined list of words, the application applies two business rules:

- **Counts** how many words start with the letter `M` or `m`
- **Returns** all words whose length is greater than 5 characters

Rules are pluggable — adding a new business rule requires no changes to existing code.

---

## Project Structure

```
wordcount-app/
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── com/ashutosh/wordcount/
    │           ├── App.java                          ← Entry point
    │           ├── model/
    │           │   └── Result.java                   ← Immutable output holder
    │           ├── rules/
    │           │   ├── WordRule.java                 ← Functional interface (contract)
    │           │   ├── RuleType.java                 ← Enum of available rules
    │           │   ├── WordRuleFactory.java           ← Factory (maps type to rule)
    │           │   ├── StartsWithMRule.java           ← Rule: count M/m words
    │           │   └── LengthGreaterThanFiveRule.java ← Rule: filter by length
    │           └── service/
    │               └── WordProcessor.java             ← Pipeline executor
    └── test/
        └── java/
            └── com/ashutosh/wordcount/
                ├── AppTest.java
                ├── model/
                │   └── ResultTest.java
                ├── rules/
                │   ├── StartsWithMRuleTest.java
                │   ├── LengthGreaterThanFiveRuleTest.java
                │   └── WordRuleFactoryTest.java
                └── service/
                    └── WordProcessorTest.java
```

---

## Architecture

The design follows a three-layer mental model:

| Layer | Responsibility | Technique |
|---|---|---|
| Define rule | How logic is written | Strategy class / Lambda |
| Select rule | Which rule to use | Factory |
| Execute rules | How many rules run | Pipeline |

```
App.java
   │
   │  asks Factory for rules
   ▼
WordRuleFactory.get(RuleType.X)       ← selects cached rule
   │
   ▼
StartsWithMRule / LengthGreaterThanFiveRule  ← contains logic
   │
   │  passed into
   ▼
WordProcessor.process(words, rules)   ← executes pipeline
   │
   ▼
Result                                ← immutable output
```

---

## Business Rules

| Rule | Input | Output |
|---|---|---|
| `StartsWithMRule` | List of words | `Long` — count of words starting with M or m |
| `LengthGreaterThanFiveRule` | List of words | `List<String>` — words longer than 5 characters |

### Adding a new rule

1. Add a constant to `RuleType.java`
2. Create a new class implementing `WordRule<T>` in `rules/`
3. Add a case to `WordRuleFactory`

No other files need to change — this is the **Open/Closed Principle** in practice.

---

## Tech Stack

| Tool | Version |
|---|---|
| Java | 21 (LTS) |
| Maven | 3.x |
| JUnit Jupiter | 5.10.2 |
| SLF4J + Logback | 2.0.12 / 1.5.3 |
| Spotless | 2.43.0 |

---

## Getting Started

### Prerequisites

- Java 21
- Maven 3.x

### Clone the repository

```bash
git clone https://github.com/AshutosMishra/wordcount-app.git
cd wordcount-app
```

### Build the project

```bash
mvn clean package
```

### Run the application

```bash
java -cp target/wordcount-app-0.0.1-SNAPSHOT.jar com.ashutosh.wordcount.App
```

### Expected output

```
Words starting with M/m: 4
Words longer than 5 characters: [Strawberry, Mandarin, blueberry]
```

---

## Running Tests

```bash
mvn clean test
```

All test classes follow JUnit 5 conventions with `@DisplayName` for readable output.

| Test Class | Covers |
|---|---|
| `AppTest` | Application entry point |
| `StartsWithMRuleTest` | Counting logic, edge cases, empty list |
| `LengthGreaterThanFiveRuleTest` | Filtering logic, boundary values, order |
| `WordRuleFactoryTest` | Rule resolution, caching, null safety |
| `ResultTest` | Output retrieval, Optional safety, null guards |
| `WordProcessorTest` | Pipeline execution, null safety, empty inputs |

---

## Generating Javadoc

```bash
mvn javadoc:javadoc
```

Opens at `doc/index.html` — all classes fully documented with zero warnings.

---

## Design Decisions

**Why Strategy Pattern?**
Each business rule is encapsulated in its own class implementing `WordRule<T>`. The processor never knows what rules do — it just runs them. New rules can be added without touching existing code.

**Why a Factory?**
`WordRuleFactory` centralises rule creation. The caller never instantiates rules directly, which decouples `App` from implementation details. Rules are cached as singletons since they are stateless.

**Why `LinkedHashMap` in `WordProcessor`?**
Preserves insertion order — results appear in the same order rules were applied, making output predictable and tests reliable.

**Why `List.copyOf()` over `Collections.unmodifiableList()`?**
`List.copyOf()` takes a true snapshot — external mutations to the original list after processing begins cannot affect the copy. Full immutability guaranteed.

**Why switch-based Factory over Map-based?**
The switch expression gives compile-time exhaustiveness checking. If a new `RuleType` is added without a corresponding case, Java reports an error at compile time — not silently at runtime.
