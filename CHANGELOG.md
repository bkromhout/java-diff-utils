# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- Refined `README.md`

## [2.2.0] – 2018-06-26

### Changed

- Main repository is now <https://github.com/java-diff-utils/java-diff-utils/>
- The groupid is now `io.github.java-diff-utils`
- Published on maven central

### Fixed

- Empty-context add-only patches were applied in the wrong place, typically one line early

## [2.1.1] – 2016-10-24

### Added

- Added some unit tests.

### Changed

- Maven group is `com.github.bkromhout`

### Fixed

- Bugfix: Fix issue showing inline diffs.

## [2.1.0] – 2016-10-24

### Changed

- Removes the dependency on Guava time

## [2.0.0] – 2016-02-24

### Added

- Add the ability to differentiate the inserted and deleted tags and class-names in inline-diff
- Add tha ability to skip data
- Can now customize diff algorithm in `DiffRowGenerator.Builder`

### Fixed

- Fix "equal" lines when lines isn't really equals (when Equalizer return equals on different strings)
- Fix imbrication tag bug in lineDiff (when inline is on a multi-line chunk)

### Changed

- Change groupId and artifactId to prevent conflict with origin library: now 'com.github.java-diff-utils:java-diff-utils' instead of 'jp.skypencil.java-diff-utils:diffutils'
- Default class-name is now `null` for deleted and inserted data, and "`change`" for change data
- Default tag for deleted data is `del`
- Default tag for inserted data is `ins`
- Changed build system from Maven to Gradle

## 1.5.0 – 2014-05-22

### Changed

- make Equalizer configurable. ([pull #1](https://github.com/eller86/java-diff-utils/pull/1))

## 1.4.1 – 2014-01-01

### Fixed

- bugfix: parse method should be public

## 1.4.0 – 2014-01-01

### Added

- add Guava to dependency
- let user uses other string to represent line which does not exist
- implement event based parser like SAX (in difflib.event package)

### Changed

- switch from JDK5 to JDK7

[Unreleased]: https://github.com/java-diff-utils/java-diff-utils/compare/2.2.0...HEAD
[2.2.0]: https://github.com/java-diff-utils/java-diff-utils/compare/2.1.1...2.2.0
[2.1.1]: https://github.com/java-diff-utils/java-diff-utils/compare/2.1.0...2.1.1
[2.1.0]: https://github.com/java-diff-utils/java-diff-utils/compare/2.0.0...2.1.0
[2.0.0]: https://github.com/java-diff-utils/java-diff-utils/compare/1.5.0...2.0.0
[1.5.0]: https://github.com/java-diff-utils/java-diff-utils/compare/1.4.1...1.5.0
[1.4.1]: https://github.com/java-diff-utils/java-diff-utils/compare/1.4.0...1.4.1
