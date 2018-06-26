# java-diff-utils [![Build Status](https://travis-ci.org/java-diff-utils/java-diff-utils.svg?branch=master)](https://travis-ci.org/java-diff-utils/java-diff-utils)

> A library for computing diffs, applying patches, generation side-by-side view in Java.

`java-diff-utils` is an open source library for performing comparison operations between chunks of text: computing diffs, applying patches, generating unified diffs or parsing them, generating diff output for easy future display (like a side-by-side view) and so on.

The main reason for creating this library was the lack of easy-to-use libraries with all the usual features necessary for working with diff files.
Originally it was inspired by the [JRCS library](https://bitbucket.org/apalala/jrcs) and its nice diff module design.

**Original code and docs were forked from:** [java-diff-utils](https://code.google.com/p/java-diff-utils/).
The original author created his own fork at <https://github.com/dnaumenko/java-diff-utils>.

## Main Features

* Computing the difference between two chunks of text.
* Capable of handling more than plain ASCII. Arrays or Lists of any type that implement hashCode() and equals() correctly can be used as inputs for differencing using this library.
* Patch and unpatch the text with the given patch.
* Parsing the unified diff format.
* Producing human-readable differences.

## Installation

Since version 2.2.0, this fork of java-diff-utils is hosted on Maven Central.
This means you can add java-diff-utils as a dependency to your project using Gradle.

Add java-diff-utils to your dependencies like this:

```groovy
dependencies {
    compile 'io.github.java-diff-utils:java-diff-utils:2.2.0'
}
```

## Algorithms

This library implements Myers' diff algorithm, but it is modular so it is easy to replace the algorithm with another which might be better suited to your needs.

## Tutorial

* In Spanish: [Comparar Ficheros java-diff-utils](https://www.adictosaltrabajo.com/tutoriales/comparar-ficheros-java-diff-utils/)

## License

This work is licensed under The Apache Software License, Version 1.1.

Reason: The code contains work of HP, which contributed it under Apache-1.1.
[[Example code](https://github.com/apache/wicket/blob/master/wicket-util/src/main/java/org/apache/wicket/util/diff/Delta.java)].
It was easier to change the license to Apache-1.1 than to contact HP Legal for a code created in 2003 at HP Bristol.

I had a discussion with [Juancarlo](https://bitbucket.org/apalala/) and Brian McBride.
Brian worked for HPLabs in Bristol (UK) at that time. His authorship appears in three files:

- https://github.com/apache/wicket/blob/master/wicket-util/src/main/java/org/apache/wicket/util/diff/DiffAlgorithm.java
- https://github.com/apache/wicket/blob/master/wicket-util/src/main/java/org/apache/wicket/util/diff/Revision.java
- https://github.com/apache/wicket/blob/master/wicket-util/src/main/java/org/apache/wicket/util/diff/Delta.java

All other files at JRCS are (according to Juancarlo) 100% authored by Juancarlo in a cleanroom way.

Since Brian pointed me to HP and I do not want to fight with HP, I agreed with Juancarlo to change the license of the JRCS files to Apache-1.1.

More reading on JRCS and java-diff-utils license issues: https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=696165 and https://bugzilla.redhat.com/show_bug.cgi?id=1013039.

The alternative to Apache-1.1 license is to find out which code was written by Brian and to replace this code by some other clean room developed code.
