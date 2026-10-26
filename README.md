# String Matching Algorithms in Java
### Knuth–Morris–Pratt (KMP), Rabin–Karp & Aho–Corasick

A comprehensive implementation of three fundamental string-matching algorithms in Java, focusing on efficient pattern searching, preprocessing techniques, rolling hashes, and multi-pattern matching.

This repository demonstrates how different algorithmic strategies solve the problem of finding patterns inside larger text efficiently.

## Overview

String matching is a fundamental problem in computer science. Given a text string and one or more patterns, the objective is to determine whether those patterns exist in the text and identify their occurrence positions.

This repository implements three classical approaches:

- **Knuth–Morris–Pratt (KMP):** Uses prefix-function preprocessing to avoid redundant character comparisons.
- **Rabin–Karp:** Uses rolling hashes to efficiently identify potential pattern matches.
- **Aho–Corasick:** Uses a trie and failure links to search for multiple patterns simultaneously in a single pass through the text.

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

KMP preprocesses the pattern, constructs its LPS array, and uses it to continue matching efficiently after mismatches.

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
- Has expected linear-time performance under suitable hashing assumptions.
- Can degrade to quadratic time in the worst case due to repeated hash collisions and character verification.

### 3. Aho–Corasick Algorithm

Aho–Corasick is a multi-pattern string-matching algorithm that efficiently searches for multiple patterns simultaneously within a single text.

Unlike KMP, which typically searches for one pattern at a time, Aho–Corasick can find occurrences of many patterns in one traversal of the text.

#### Core Concept

The algorithm combines a Trie data structure with failure links.

It consists of three main components:

1. **Trie Construction:** Insert all patterns into a trie, sharing common prefixes.
2. **Failure Link Construction:** Use Breadth-First Search (BFS) to build failure links that determine where to continue matching after a mismatch.
3. **Pattern Matching:** Traverse the text once, following trie transitions and failure links to identify matching patterns.

When a match is found, the algorithm reports the corresponding pattern occurrence.

Failure links also allow the algorithm to identify patterns that end at the same position, including patterns that are suffixes of longer patterns.

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

The algorithm processes the text in a single traversal while identifying occurrences of all the supplied patterns.

#### Key Characteristics

- Supports simultaneous matching of multiple patterns.
- Uses a trie for efficient pattern representation.
- Uses failure links to avoid restarting the search.
- Constructs failure links using BFS.
- Is useful for dictionary matching, keyword detection, and text scanning.

## KMP vs. Rabin–Karp vs. Aho–Corasick

| Feature | KMP | Rabin–Karp | Aho–Corasick |
|---|---|---|---|
| Primary technique | LPS array | Rolling hash | Trie and failure links |
| Main use case | Single-pattern matching | Hash-based pattern matching | Multi-pattern matching |
| Preprocessing | Pattern preprocessing | Pattern and window hashing | Trie and failure-link construction |
| Search complexity | O(n) | Expected O(n + m) | O(n + z) after preprocessing |
| Worst-case complexity | O(n + m) total | O(nm) | O(n + z) after preprocessing |
| Extra space | O(m) | O(1) for a fixed-size rolling hash | O(S × alphabet size) for a dense trie |
| Hash collisions | Not applicable | Possible | Not applicable |
| Deterministic performance | Yes | Not guaranteed to be linear | Yes |
| Multiple patterns | Run separately for each pattern | Can process multiple patterns using hashing | Designed for multiple patterns |

Here:

- `n` = length of the text.
- `m` = length of a single pattern.
- `S` = total number of characters across all patterns.
- `z` = total number of reported pattern occurrences.
- `alphabet size` = number of supported characters.

For Aho–Corasick, the total complexity includes preprocessing the patterns and constructing failure links. With a fixed alphabet and a standard trie implementation, construction is linear in the total pattern length, and searching takes `O(n + z)` time.

## Complexity Analysis

### KMP

| Operation | Complexity |
|---|---|
| LPS array construction | O(m) |
| Pattern matching | O(n) |
| Total time complexity | O(n + m) |
| Auxiliary space | O(m) |

KMP guarantees linear-time matching by reusing prefix information instead of repeatedly restarting the search.

### Rabin–Karp

| Operation | Complexity |
|---|---|
| Initial hash calculation | O(m) |
| Rolling-hash update per window | O(1) |
| Average-case search | Expected O(n + m) |
| Worst-case search | O(nm) |
| Auxiliary space | O(1) for a fixed-size rolling hash |

Worst-case performance can deteriorate when many windows produce the same hash and require character-by-character verification.

### Aho–Corasick

| Operation | Complexity |
|---|---|
| Trie construction | O(S) |
| Failure-link construction | O(S × alphabet size) for dense transitions |
| Text searching | O(n + z) |
| Total time complexity | O(S × alphabet size + n + z) |
| Auxiliary space | O(S × alphabet size) for a dense trie |

For a fixed alphabet, the construction and search complexities simplify to `O(S + n + z)`.

A sparse trie representation can reduce memory usage when the supported alphabet is large.

## Why Not Use Naive String Matching?

The naive approach checks the pattern at every possible starting position in the text.

For one pattern, its worst-case time complexity is:

`O(n × m)`

For multiple patterns, repeatedly applying naive matching can become even more expensive.

These algorithms reduce unnecessary work using different techniques:

- KMP reuses prefix information.
- Rabin–Karp uses rolling hashes to filter candidate windows.
- Aho–Corasick searches for multiple patterns simultaneously using trie transitions and failure links.

## Practical Applications

These algorithms demonstrate techniques used in a variety of string-processing problems:

- Text searching and pattern detection.
- Search engines and document processing.
- Source-code analysis.
- DNA sequence analysis.
- Log analysis and event detection.
- Duplicate and similarity detection.
- Dictionary matching.
- Keyword filtering and content scanning.
- Multi-pattern searching in large text collections.

Aho–Corasick is particularly useful when a large set of keywords must be searched within the same text.

## Engineering Considerations

When implementing string matching in Java, consider the following:

1. **Empty patterns:** Define whether an empty pattern should match at index `0` or be rejected.
2. **Input validation:** Handle null inputs according to the API contract.
3. **Character representation:** Java `char` represents a UTF-16 code unit, not necessarily a complete Unicode code point.
4. **Hash collisions:** Verify actual characters whenever Rabin–Karp hash values match.
5. **Integer overflow:** Use appropriate arithmetic and modulus operations for rolling hashes.
6. **Failure links:** Ensure Aho–Corasick correctly handles suffix patterns and overlapping matches.
7. **Testing:** Include repeated characters, overlapping matches, patterns longer than the text, and patterns absent from the text.
8. **Memory usage:** Select an appropriate trie representation based on the supported alphabet and number of patterns.

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

For Aho–Corasick, additional tests should include:

- Multiple patterns sharing a common prefix.
- One pattern being a suffix of another.
- Multiple patterns ending at the same text position.
- Patterns that do not occur in the text.
- Duplicate patterns, if supported by the implementation.

## Repository Structure

```text
String-Matching-Algorithms/
├── KMPALGO.java
├── RabinKarp.java
├── AhoCorasick.java
└── README.md
```

Adjust the filenames above to match the actual files in your GitHub repository.

## Learning Outcomes

By studying these implementations, developers can understand:

- How prefix information reduces redundant comparisons.
- How rolling hashes accelerate pattern-window comparisons.
- How tries represent collections of patterns efficiently.
- How BFS constructs failure links in Aho–Corasick.
- The difference between deterministic and expected performance.
- How preprocessing improves algorithmic efficiency.
- Why worst-case analysis matters when selecting an algorithm.
- How to evaluate algorithms using time and space complexity.

## Conclusion

KMP, Rabin–Karp, and Aho–Corasick demonstrate three distinct approaches to optimizing string matching.

KMP uses structural information within a pattern to guarantee linear-time matching. Rabin–Karp uses rolling hashes to efficiently identify candidate matches. Aho–Corasick combines a trie with failure links to search for multiple patterns in a single traversal of the text.

Understanding these algorithms provides a strong foundation for advanced string processing, competitive programming, and algorithmic problem-solving.

---

Developed in Java | Data Structures & Algorithms | String Processing
