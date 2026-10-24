# String Matching Algorithms in Java
### Knuth–Morris–Pratt (KMP) & Rabin–Karp

A comprehensive implementation of two fundamental string-matching algorithms in Java, focusing on efficient pattern searching, preprocessing techniques, hashing, and optimized character comparisons.

This repository demonstrates how different algorithmic strategies solve the same fundamental problem: finding a pattern inside a larger text efficiently.

## Overview

String matching is a fundamental problem in computer science. Given a text string and a pattern, the objective is to determine whether the pattern exists in the text and identify its occurrence positions.

This repository implements two classical approaches:

- Knuth–Morris–Pratt (KMP): Uses prefix-function preprocessing to avoid redundant character comparisons.
- Rabin–Karp: Uses rolling hash calculations to efficiently identify potential pattern matches.

Both algorithms improve upon the naive string-matching approach, but they use different techniques to optimize the search process.

## Algorithms Implemented

### 1. Knuth–Morris–Pratt (KMP)

KMP is a deterministic string-matching algorithm that uses information about previously matched characters to avoid unnecessary comparisons.

#### Core Concept

The algorithm preprocesses the pattern to construct an LPS array.

LPS stands for Longest Proper Prefix which is also a Suffix.

For each position in the pattern, the LPS array stores the length of the longest proper prefix that is also a suffix for the substring ending at that position.

When a mismatch occurs, KMP uses this information to determine how far the pattern can shift without rechecking characters that are already known to match.

#### Example

Text:
`ABABDABACDABABCABAB`

Pattern:
`ABABCABAB`

KMP preprocesses the pattern, constructs its LPS array, and uses the array to continue matching efficiently after mismatches.

#### Key Characteristics

- Avoids unnecessary backtracking in the text.
- Uses prefix-function preprocessing.
- Guarantees linear-time matching.
- Does not rely on hashing.
- Provides deterministic worst-case performance.

### 2. Rabin–Karp

Rabin–Karp is a string-matching algorithm based on hashing.

Instead of comparing every character immediately, it calculates a hash value for the pattern and compares it with the hash values of text windows of the same length.

A rolling hash allows the algorithm to update the hash efficiently when the window moves by one character.

#### Core Concept

For a text of length `n` and a pattern of length `m`:

1. Calculate the hash of the pattern.
2. Calculate the hash of the first text window of length `m`.
3. Compare the two hash values.
4. If the hashes match, verify the characters to confirm an actual match.
5. Update the window hash using the rolling-hash technique.
6. Repeat until all possible windows have been processed.

Character verification is important because different strings can produce the same hash value, resulting in hash collisions.

#### Key Characteristics

- Uses hashing to compare pattern-sized windows.
- Supports efficient rolling-hash updates.
- Can be useful when searching for multiple patterns.
- Has an average-case linear-time bound under typical hashing assumptions.
- Can degrade to quadratic time in the worst case due to repeated hash collisions and character verification.

## KMP vs. Rabin–Karp

| Feature | KMP | Rabin–Karp |
|---|---|---|
| Primary technique | Prefix-function / LPS array | Rolling hash |
| Preprocessing | Pattern preprocessing | Pattern and initial-window hashing |
| Worst-case time | O(n + m) | O(nm) |
| Typical time | O(n + m) | Expected O(n + m) under suitable hashing assumptions |
| Extra space | O(m) | O(1) auxiliary space for a fixed-size rolling hash, excluding implementation-specific storage |
| Hash collisions | Not applicable | Possible |
| Character verification | Direct comparisons | Required when hashes match |
| Best suited for | Reliable single-pattern matching | Applications involving one or multiple pattern searches |

Here:
- `n` = length of the text.
- `m` = length of the pattern.

## Complexity Analysis

### KMP

| Operation | Complexity |
|---|---|
| LPS array construction | O(m) |
| Pattern matching | O(n) |
| Total time complexity | O(n + m) |
| Auxiliary space | O(m) |

KMP provides a guaranteed linear-time solution because every text character is processed efficiently without repeatedly restarting the search from the beginning.

### Rabin–Karp

| Operation | Complexity |
|---|---|
| Initial hash calculation | O(m) |
| Rolling-hash update per window | O(1) |
| Average-case search | O(n + m), under suitable hashing assumptions |
| Worst-case search | O(nm) |
| Auxiliary space | O(1) for a fixed-size rolling hash |

The worst-case complexity can increase when many windows share the pattern's hash value and require character-by-character verification.

## Why Not Use Naive String Matching?

The naive approach checks the pattern at every possible starting position in the text.

Its worst-case time complexity is:

`O(n × m)`

For large inputs, repeated comparisons can become expensive.

KMP reduces this overhead by reusing prefix information, while Rabin–Karp uses rolling hashes to filter out windows that cannot match the pattern.

## Practical Applications

These algorithms illustrate techniques used in a variety of string-processing problems:

- Text searching and pattern detection.
- Search engines and document processing.
- Source-code analysis.
- DNA sequence analysis.
- Log analysis and event detection.
- Duplicate and similarity detection.
- Multi-pattern searching, particularly with hashing-based approaches.

Actual production systems may use more specialized algorithms or optimized library implementations depending on the workload.

## Engineering Considerations

When implementing string matching in Java, consider the following:

1. Empty patterns: Define whether an empty pattern should match at index `0` or be rejected.
2. Input validation: Handle null inputs according to the API contract.
3. Character representation: Java `char` represents a UTF-16 code unit, not necessarily a complete Unicode code point.
4. Hash collisions: Always verify actual characters when a Rabin–Karp hash matches.
5. Integer overflow: Use appropriate arithmetic and modulus operations when implementing rolling hashes.
6. Testing: Include repeated characters, overlapping matches, patterns longer than the text, and patterns absent from the text.

## Testing Scenarios

Recommended test cases include:

| Scenario | Text | Pattern |
|---|---|---|
| Basic match | `hello world` | `world` |
| Pattern at beginning | `abcdef` | `abc` |
| Pattern at end | `abcdef` | `def` |
| No match | `abcdef` | `xyz` |
| Repeated characters | `aaaaaa` | `aaa` |
| Overlapping matches | `abababa` | `aba` |
| Pattern longer than text | `abc` | `abcdef` |
| Exact match | `java` | `java` |

For overlapping matches, the expected matching indices for `aba` in `abababa` are `0`, `2`, and `4`.

## Repository Structure

The repository contains Java implementations of the two string-matching algorithms.

```text
String-Matching-Algorithms/
├── KMPALGO.java
├── RabinKarp.java
└── README.md
```

Adjust the filenames above to match the actual files in your GitHub repository.

## Learning Outcomes

By studying these implementations, developers can understand:

- How prefix information reduces redundant comparisons.
- How rolling hashes accelerate pattern-window comparisons.
- The difference between deterministic and expected performance.
- How preprocessing improves algorithmic efficiency.
- Why worst-case analysis matters when selecting an algorithm.
- How to evaluate algorithms using time and space complexity.

## Conclusion

KMP and Rabin–Karp demonstrate two distinct approaches to optimizing string matching.

KMP uses structural information within the pattern to guarantee linear-time matching. Rabin–Karp uses rolling hashes to efficiently compare candidate windows, making it particularly useful when hashing can simplify multiple-pattern searches.

Understanding both algorithms provides a strong foundation for advanced string processing, competitive programming, and algorithmic problem-solving.

---

Developed in Java | Data Structures & Algorithms | String Processing
