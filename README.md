## Dlight Interview Solution

### Architecture
Solution App using [Guide to app architecture](https://developer.android.com/topic/libraries/architecture/), in 100% Kotlin, using Android Jetpack Components.

### Prerequisites

Before running this app, you need to add your Github Personal Access Token, in your `local.properties` file:

```yaml
GITHUB_TOKEN="xxxx-xxxx-xxx"
```
Before every commit, make sure you run the following bash script:

```shell script
./codeAnalysis
```


### Background

Develop an application that uses Github REST APIs to achieve the following features:
1. Search for a user profile on Github and display his account details, including, but not
   limited to, image, bio, username and number of repositories.
2. Have the ability to display the user followers, and following lists. (the accounts that
   the user is following, and the accounts that are followed by the selected user)
3. Display a detail page of the user's repositories and display the repository details,
   including, but not limited to, name, stars and description.
4. This information should be saved in the database for offline use

## Tech-stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - handle the stream of data asynchronously that executes sequentially.
    * [KOIN](https://insert-koin.io/) - a pragmatic lightweight dependency injection framework.
    * [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and the JVM
    * [Chuck](https://github.com/jgilfelt/chuck) - An in-app HTTP inspector for Android OkHttp clients
    * [Jetpack](https://developer.android.com/jetpack)
        * [Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for building native UI. 
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - a persistence library provides an abstraction layer over SQLite.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.
    * [Timber](https://github.com/JakeWharton/timber) - a highly extensible android logger.
    * [Leak Canary](https://github.com/square/leakcanary) - a memory leak detection library for Android.

* Architecture
    * Clean architecture with ViewModel, UseCases and Repositories
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/)) - a simple framework to write repeatable tests.
    * [MockK](https://github.com/mockk) - mocking library for Kotlin
    * [Kluent](https://github.com/MarkusAmshove/Kluent) - Fluent Assertion-Library for Kotlin
    * [Kakao](https://github.com/agoda-com/Kakao) - Nice and simple DSL for Espresso in Kotlin
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For reference purposes, here's an [article explaining the migration](https://medium.com/@evanschepsiror/migrating-to-kotlin-dsl-4ee0d6d5c977).
    * Plugins
        * [Ktlint](https://github.com/JLLeitschuh/ktlint-gradle) - creates convenient tasks in your Gradle project that run ktlint checks or do code auto format.
        * [Detekt](https://github.com/detekt/detekt) - a static code analysis tool for the Kotlin programming language.
        * [Spotless](https://github.com/diffplug/spotless) - format java, groovy, markdown and license headers using gradle.
        * [Dokka](https://github.com/Kotlin/dokka) - a documentation engine for Kotlin, performing the same function as javadoc for Java.
        * [jacoco](https://github.com/jacoco/jacoco) - a Code Coverage Library