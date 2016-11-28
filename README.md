# java-diff-utils

The java-diff-utils library is for computing diffs, applying patches, generation side-by-side view in Java.

It is an open source library for performing comparison operations between chunks of text: computing diffs, applying patches, generating unified diffs or parsing them, generating diff output for easy future display (like a side-by-side view) and so on.

The main reason for creating this library was the lack of easy-to-use libraries with all the usual features necessary for working with diff files. Originally it was inspired by the JRCS library and its nice diff module design.

**Original code and docs were forked from:** [java-diff-utils](https://code.google.com/p/java-diff-utils/).
The original author created his own fork at <https://github.com/dnaumenko/java-diff-utils>.

## Main Features

* Computing the difference between two chunks of text.
* Capable of handling more than plain ASCII. Arrays or Lists of any type that implement hashCode() and equals() correctly can be used as inputs for differencing using this library.
* Patch and unpatch the text with the given patch.
* Parsing the unified diff format.
* Producing human-readable differences.

## Installation

Since version 2.1.1, this fork of java-diff-utils is hosted on Bintray's jcenter. This means you can add java-diff-utils as a dependency to your project using Gradle.

Ensure that you have `jcenter()` defined as a repository:
```groovy
repositories {
    jcenter()
}
```

Then add java-diff-utils to your dependencies like this:
```groovy
dependencies {
    compile 'com.github.bkromhout:java-diff-utils:{latest version}'
}
```

## Algorithms

This library implements Myers' diff algorithm, but it is modular so it is easy to replace the algorithm with another which might be better suited to your needs.

# Tutorial

* In Spanish: [Comparar Ficheros java-diff-utils](https://www.adictosaltrabajo.com/tutoriales/comparar-ficheros-java-diff-utils/)

## Changelog

### 2.1.1

- Bugfix: Fix issue showing inline diffs.
- Added some unit tests.

### 2.1.0

- Removes the dependency on Guava time

### 2.0.0

- Change groupId and artifactId to prevent conflict with origin library: now 'com.github.java-diff-utils:java-diff-utils' instead of 'jp.skypencil.java-diff-utils:diffutils'
- Adds the ability to differentiate the inserted and deleted tags and class-names in inline-diff
- Default class-name is now `null` for deleted and inserted data, and "`change`" for change data
- Default tag for deleted data is `del`
- Default tag for inserted data is `ins`
- can now customize diff algorithm in `DiffRowGenerator.Builder`
- fix "equal" lines when lines isn't really equals (when Equalizer return equals on different strings)
- fix imbrication tag bug in lineDiff (when inline is on a multi-line chunk)
- Adds tha ability to skip data

### 1.5.0

- make Equalizer configurable. ([pull #1](https://github.com/eller86/java-diff-utils/pull/1))

### 1.4.1

- bugfix: parse method should be public

### 1.4.0

- switch from JDK5 to JDK7
- add Guava to dependency
- let user uses other string to represent line which does not exist
- implement event based parser like SAX (in difflib.event package)

### License

The license of the library is Apache-2.0.
Until version 1.3.1, the license was Apache-1.1.
The files in the directory `java-diff-utils-lib/src/main/java/difflib/myers` are subject to LGPL-2.1.
