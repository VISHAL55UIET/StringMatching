\# String Matching Algorithms in Java

### KMP | Rabin–Karp | Aho–Corasick | Boyer–Moore   

A comprehensive collection of classical string-matching algorithms implemented in Java, covering efficient pattern searching, prefix-function preprocessing, rolling hashes, trie-based multi-pattern matching, and character-based pattern shifting.

This repository demonstrates how different algorithmic strategies solve the problem of finding patterns within larger text efficiently.

## Overview

String matching is a fundamental problem in computer science. Given a text string and one or more patterns, the objective is to determine whether those patterns occur in the text and identify their starting positions.

This repository implements four classical string-matching algorithms:

- Knuth–Morris–Pratt (KMP): Uses an LPS array to avoid redundant character comparisons.
- Rabin–Karp: Uses rolling hashes to identify potential pattern matches efficiently.
- Aho–Corasick: Uses a trie and failure links to search for multiple patterns in a single traversal of the text./
- Boyer–Moore: Compares characters from right to left and uses pattern-shifting heuristics to skip unnecessary comparisons.

## Algorithms Implemented

### 1. Knuth–Morris–Pratt (KMP)

KMP is a deterministic string-matching algorithm that avoids unnecessary comparisons by reusing information from previously matched characters.

#### Core Concept

KMP preprocesses the pattern to construct an LPS array.

LPS stands for Longest Proper Prefix which is also a Suffix.

For each position in the pattern, the LPS array stores the length of the longest proper prefix that is also a suffix of the substring ending at that position.

When a mismatch occurs, KMP uses the LPS array to determine how far the pattern can shift without rechecking characters already known to match.

#### Example

Text:

`ABABDABACDABABCABAB`

Pattern:

`ABABCABAB`

KMP constructs the LPS array and uses it to continue matching efficiently after mismatches.

#### Key Characteristics

- Uses prefix-function preprocessing.
- Avoids unnecessary backtracking in the text.
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

Character verification is necessary because different strings can produce the same hash value, resulting in hash collisions.

#### Key Characteristics

- Uses rolling hashes to compare pattern-sized windows.
- Supports efficient hash updates.
- Can be adapted for searching multiple patterns.
- Has expected linear-time performance under suitable hashing assumptions.
- Can degrade to quadratic time in the worst case because of repeated hash collisions and character verification.

### 3. Aho–Corasick

Aho–Corasick is a multi-pattern string-matching algorithm that efficiently searches for multiple patterns simultaneously within a single text.

Unlike KMP, which typically searches for one pattern at a time, Aho–Corasick can identify occurrences of many patterns during one traversal of the text.

#### Core Concept

The algorithm combines a Trie data structure with failure links.

It consists of three main stages:

1. Trie Construction: Insert all patterns into a trie, sharing common prefixes.
2. Failure-Link Construction: Use Breadth-First Search (BFS) to build failure links that determine where matching should continue after a mismatch.
3. Pattern Matching: Traverse the text, following trie transitions and failure links to identify matching patterns.

Failure links also allow the algorithm to identify patterns that are suffixes of longer patterns.

#### Example

Text:

`ahishers`

Patterns:

`he`, `she`, `his`, `hers`

Expected matches:

- `his` at index `1`
- `she` at index `3`
- `he` at index `4`
- `hers` at index `4`

The algorithm searches for all supplied patterns during a single traversal of the text.

#### Key Characteristics

- Supports simultaneous matching of multiple patterns.
- Uses a trie for efficient pattern representation.
- Uses failure links to avoid restarting the search.
- Constructs failure links using BFS.
- Is useful for dictionary matching, keyword detection, and text scanning.

### 4. Boyer–Moore

Boyer–Moore is an efficient string-matching algorithm that compares pattern characters from right to left rather than from left to right.

When a mismatch occurs, the algorithm uses shifting heuristics to skip positions where a match cannot occur.

#### Core Concept

Boyer–Moore commonly uses two heuristics:

1. Bad-Character Heuristic: Shifts the pattern based on the position of the mismatched text character in the pattern.
2. Good-Suffix Heuristic: Uses an already matched suffix to determine a safe shift based on other occurrences of that suffix in the pattern.

This repository's basic implementation uses the bad-character heuristic.

#### Example

Text:

`ABAAABCD`

Pattern:

`ABC`

Expected first occurrence:

Index `4`

The algorithm compares the pattern from right to left and shifts the pattern when a mismatch occurs.

#### Key Characteristics

- Compares pattern characters from right to left.
- Uses the bad-character heuristic to skip unnecessary comparisons.
- Can use the good-suffix heuristic for additional improvements.
- Often performs very well on long patterns and large texts.
- Has a worst-case time complexity of `O(n × m)` for the basic bad-character-only implementation.

## Algorithm Comparison

| Feature | KMP | Rabin–Karp | Aho–Corasick | Boyer–Moore |
|---|---|---|---|---|
| Primary technique | LPS array | Rolling hash | Trie and failure links | Pattern-shifting heuristics |
| Main use case | Single-pattern matching | Hash-based pattern matching | Multi-pattern matching | Efficient single-pattern searching |
| Matching direction | Left to right | Window-based | Left to right | Right to left |
| Preprocessing | LPS construction | Pattern hashing | Trie and failure-link construction | Bad-character table |
| Typical search performance | O(n) | Expected O(n + m) | O(n + z) after preprocessing | Often sublinear in practice |
| Worst-case search time | O(n) | O(n × m) | O(n + z) after preprocessing | O(n × m) for the basic implementation |
| Hash collisions | Not applicable | Possible | Not applicable | Not applicable |
| Multiple patterns | Usually processed separately | Can be adapted | Designed for multiple patterns | Usually processed separately |
| Deterministic matching | Yes | Not guaranteed to be linear | Yes | Yes |

Here:

- `n` = length of the text.
- `m` = length of a pattern.
- `z` = total number of pattern occurrences reported.
- `S` = total length of all patterns supplied to Aho–Corasick.

The comparison assumes standard implementations. Actual performance depends on the input, alphabet, preprocessing strategy, and implementation details.

## Complexity Analysis

### KMP

| Operation | Complexity |
|---|---|
| LPS array construction | O(m) |
| Pattern matching | O(n) |
| Total time | O(n + m) |
| Auxiliary space | O(m) |

KMP guarantees linear-time matching by reusing prefix information instead of restarting the search.

### Rabin–Karp

| Operation | Complexity |
|---|---|
| Initial hash calculation | O(m) |
| Rolling-hash update per window | O(1) |
| Expected search time | O(n + m) |
| Worst-case search time | O(n × m) |
| Auxiliary space | O(1) for a fixed-size rolling hash |

Worst-case performance can deteriorate when many windows produce the same hash and require character-by-character verification.

### Aho–Corasick

| Operation | Complexity |
|---|---|
| Trie construction | O(S) |
| Failure-link construction | O(S × alphabet size) for a dense trie |
| Text searching | O(n + z) |
| Total time | O(S × alphabet size + n + z) |
| Auxiliary space | O(S × alphabet size) for a dense trie |

For a fixed alphabet, preprocessing and searching simplify to `O(S + n + z)`.

### Boyer–Moore

This repository's basic implementation uses the bad-character heuristic.

| Operation | Complexity |
|---|---|
| Bad-character table construction | O(m + alphabet size) |
| Pattern matching, worst case | O(n × m) |
| Auxiliary space | O(alphabet size) |

Boyer–Moore often skips multiple text positions after a mismatch, making it efficient in practice. However, the basic bad-character-only implementation does not guarantee linear worst-case matching.

The full Boyer–Moore algorithm combines the bad-character and good-suffix heuristics.

## Why Not Use Naive String Matching?

The naive approach checks the pattern at every possible starting position in the text.

Its worst-case time complexity for a single pattern is:

`O(n × m)`

For large inputs, repeated comparisons can become expensive.

The four algorithms reduce unnecessary work using different strategies:

- KMP reuses prefix information.
- Rabin–Karp uses rolling hashes to filter candidate windows.
- Aho–Corasick searches for multiple patterns simultaneously.
- Boyer–Moore skips positions using character-based shifting heuristics.

## Practical Applications

String-matching algorithms are useful in many areas of computer science.

- Text searching and pattern detection.
- Search engines and document processing.
- Source-code analysis.
- DNA sequence analysis.
- Log analysis and event detection.
- Keyword filtering and content scanning.
- Dictionary matching.
- Duplicate and similarity detection.
- Searching large text collections.
- Competitive programming and algorithmic problem-solving.

Aho–Corasick is especially useful when searching for many keywords in the same text, while Boyer–Moore can be effective for searching for a single pattern in a large text.

## Engineering Considerations

When implementing string matching in Java, consider the following:

1. Empty Patterns: Define whether an empty pattern should match at index `0` or be rejected.
2. Input Validation: Handle null inputs according to the API contract.
3. Character Representation: Java `char` represents a UTF-16 code unit, not necessarily a complete Unicode code point.
4. Hash Collisions: Verify actual characters whenever Rabin–Karp hash values match.
5. Integer Overflow: Use appropriate arithmetic and modulus operations for rolling hashes.
6. Failure Links: Ensure Aho–Corasick correctly handles suffix patterns and overlapping matches.
7. Pattern Shifting: Ensure Boyer–Moore shifts the pattern without skipping valid matches.
8. Testing: Include repeated characters, overlapping matches, patterns longer than the text, and patterns absent from the text.

## Testing Scenarios

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

For overlapping matches, the expected starting indices for `aba` in `abababa` are `0`, `2`, and `4`.

Additional Aho–Corasick tests should include multiple patterns sharing prefixes and suffixes. Boyer–Moore tests should include mismatches near the beginning and end of the pattern.

## Repository Structure

```text
String-Matching-Algorithms/
├── KMPALGO.java
├── RabinKarp.java
├── AhoCorasick.java
├── BoyerMoore.java
└── README.md
```

Adjust the filenames above to match the actual files in your GitHub repository.

## Learning Outcomes

By studying these implementations, developers can understand:

- How prefix information reduces redundant comparisons.
- How rolling hashes accelerate pattern-window comparisons.
- How tries represent collections of patterns efficiently.
- How BFS constructs failure links in Aho–Corasick.
- How bad-character shifting improves pattern searching.
- The difference between deterministic and expected performance.
- How preprocessing improves algorithmic efficiency.
- Why worst-case complexity matters when selecting an algorithm.
- How to evaluate algorithms using time and space complexity.

## Conclusion

KMP, Rabin–Karp, Aho–Corasick, and Boyer–Moore demonstrate four different approaches to efficient string matching.

KMP uses prefix information to guarantee linear-time matching. Rabin–Karp uses rolling hashes to identify candidate matches efficiently. Aho–Corasick combines a trie with failure links to search for multiple patterns in a single traversal. Boyer–Moore uses pattern-shifting heuristics to skip unnecessary comparisons.

Together, these algorithms provide a strong foundation for advanced string processing, competitive programming, and algorithmic problem-solving.

---

Developed in Java | Data Structures & Algorithms | String Processing
