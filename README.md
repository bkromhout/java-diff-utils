# (outdated) java-diff-utils [![Build Status](https://travis-ci.org/java-diff-utils/java-diff-utils.svg?branch=master)](https://travis-ci.org/java-diff-utils/java-diff-utils) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.java-diff-utils/java-diff-utils/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.java-diff-utils/java-diff-utils)  [![No Maintenance Intended](http://unmaintained.tech/badge.svg)](http://unmaintained.tech/)

> **The maintained successor if this library is https://github.com/java-diff-utils/java-diff-utils**

> A library for computing diffs, applying patches, generation side-by-side view in Java.

`java-diff-utils` is an open source library for performing comparison operations between chunks of text: computing diffs, applying patches, generating unified diffs or parsing them, generating diff output for easy future display (like a side-by-side view) and so on.

The main reason for creating this library was the lack of easy-to-use libraries with all the usual features necessary for working with diff files.
Originally it was inspired by the [JRCS library](https://bitbucket.org/apalala/jrcs) and its nice diff module design.

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

## Other variants of this library

The library was originally hosted on Google Code at <https://code.google.com/p/java-diff-utils/>.
This library is a fork originally created by @KengoTODA ([published on maven central](https://mvnrepository.com/artifact/jp.skypencil.java-diff-utils/diffutils) using the group id `jp.skypencil.java-diff-utils`) and enhanced by @bkromhout.

Since GitHub does not connect forks based on Google Code, other variants appeared in parallel:

- https://github.com/dnaumenko/java-diff-utils - Created by the original Google Code maintainer
- https://github.com/wumpz/java-diff-utils - Created from the Google Code repository [starting in 2017](https://github.com/wumpz/java-diff-utils/commit/42fde56154afd92d2b9ef8e088185e7af0230cee). Now maintained at http://github.com/java-diff-utils/java-diff-utils.

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
