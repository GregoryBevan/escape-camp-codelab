## ReaKt.EveS configuration
The goal of this code is to focus on the usage and advantage of the event sourcing pattern.

In order to help us, we are going to use a Kotlin library and a Gradle plugin that we developed.

The library can be used by herself, it gives every component (classes, interfaces...) for both CQRS and Event Sourcing pattern.

Instead of creating them manually, the Gradle plugin is a CLI that allows to generate the skeleton for domain.

### Add ReaKt.EveS dependency
- Open the _build.gradle.kts_ file
- In the **dependencies** closure, add the following dependency:
```kotlin
implementation("me.elgregos:reakt-eves:1.2.5")
```
- Reformat the file

### Add ReaKt.EveS Gradle plugin
- In the **plugins** closure of the _build.gradle.kts_ file, add the following plugin:
```kotlin
id("me.elgregos.reakteves.cli") version "1.2.5"
```

### Initialize the Game domain
In order to generate Game domain, you need to open a terminal and execute this:
- Unix/MacOs
```shell
./gradlew initDomain
```
- Windows
```
gradle initDomain
```
This can also be done using the Gradle view of IntelliJ.

### Restart the application 
See [Part 2](%232-database-configuration.md#start-the-application)

You can now check the database to see that the tables described in the _db-changelog.sql_ file are created. 

Next