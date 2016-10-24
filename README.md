# java-diff-utils

The java-diff-utils library is for computing diffs, applying patches, generation side-by-side view in Java.

It is an OpenSource library for performing the comparison operations between texts: computing diffs, applying patches, generating unified diffs or parsing them, generating diff output for easy future displaying (like side-by-side view) and so on.

Main reason to build this library was the lack of easy-to-use libraries with all the usual stuff you need while working with diff files. Originally it was inspired by JRCS library and it's nice design of diff module.

**Original code and docs were forked from:** [java-diff-utils](https://code.google.com/p/java-diff-utils/)

## Main Features

* computing the difference between two texts.
* capable to hand more than plain ascci. Arrays or List of any type that implements hashCode() and equals() correctly can be subject to differencing using this library
* patch and unpatch the text with the given patch
* parsing the unified diff format
* producing human-readable differences

## Algorithms

This library implements Myer's diff algorithm. But it can easily replaced by any other which is better for handing your texts.

# Tutorial

* In Spanish: [Comparar Ficheros java-diff-utils](https://www.adictosaltrabajo.com/tutoriales/comparar-ficheros-java-diff-utils/)

## Changelog

### 2.1.1

- Bugfix: Fix issue showing inline diffs.
- Added some unit tests.

### 2.1.0

- Removes the dependency on Guavatime

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
