# Focus on the Domain and Business logic
Now we can start to add objects and enrich the domain and business logic to represent a question in our Game.

## Review the game creation
A game should contain the riddle names and their solutions. The following steps will add this concept to the generated code.

### Add `riddleSolutions` field to `Game` entity
- **Open the `Game` data class in package `me.elgregos.escapecamp.game.domain.entity`**
- **Add the `riddleSolutions` field with type `List<Pair<String, String>>`**
```kotlin
data class Game(
    override val id: UUID,
    override val version: Int = 1,
    override val createdAt: LocalDateTime,
    override val createdBy: UUID,
    override val updatedAt: LocalDateTime = createdAt,
    override val updatedBy: UUID = createdBy,
    val riddleSolutions: List<Pair<String, String>>,
val riddles: List<Pair<String, String>>,
) : DomainEntity<UUID, UUID> {

}
```
Each pair in the list is the riddle name and its solution

### Add `riddleSolutions` parameter to `GameCreated` event
- **Open the `GameEvent` data class in package `me.elgregos.escapecamp.game.domain.event`**
- **Add the `riddleSolutions` parameter to secondary constructor with type `List<Pair<String, String>>`**
- **Add a `riddleSolutions` properties to the Json event**
```kotlin
...
 constructor(gameId: UUID, createdBy: UUID, createdAt: LocalDateTime, riddleSolutions: List<Pair<String, String>>) : this(
            gameId = gameId,
            createdAt = createdAt,
            createdBy = createdBy,
            event = genericObjectMapper.createObjectNode()
                .put("id", "$gameId")
                .put("createdAt", "$createdAt")
                .put("createdBy", "$createdBy")
                .set("riddleSolutions", genericObjectMapper.valueToTree(riddleSolutions)))
    }
...
```
The `event` field is the Json representation of the created game.

### Use the secondary constructor in `GameAggregate`
- **Open the `GameAggregate` data class in package `me.elgregos.escapecamp.game.domain.event`**
- **At top level, creates a `riddleSolutions` immutable variable**
```kotlin
val riddleSolutions: List<Pair<String, String>> = listOf(
    "riddle-1" to "solution-1",
    "riddle-2" to "solution-2",
    "riddle-3" to "solution-3",
    "riddle-4" to "solution-4"
)
```
- **In the `createGame` method, pass the riddleSolutions variable using the secondary `GameCreated` constructor**
```kotlin
fun createGame(riddleSolutions: List<Pair<String, String>>, createdAt: LocalDateTime): Flux<GameEvent> =
    Flux.just(GameCreated(gameId, userId, createdAt, riddleSolutions))
```

### Restart the application
See [Part 2](%232-database-configuration.md#start-the-application)

### Create a game
You're now able to create a game via Postman
- **Import the _postman_collection.json_ in Postman**
- **Open the Create Game POST request**
- **Send the request**

The response should be like:
```json
{
    "gameId": "8af72b1a-5b1f-4e7d-bd6f-90be309dda97"
}
```

### Read the projection of the game
- **Open the Game GET request**
- **Send the request**

The response should contain the description of the game and be like:
```json
{
    "id": "8af72b1a-5b1f-4e7d-bd6f-90be309dda97",
    "version": 1,
    "createdAt": "2023-10-17T16:53:18.667741",
    "createdBy": "81e06e6a-0986-447c-bcce-8a13f07ccadd",
    "updatedAt": "2023-10-17T16:53:18.667741",
    "updatedBy": "81e06e6a-0986-447c-bcce-8a13f07ccadd",
    "riddles": [
        {
            "first": "riddle-1",
            "second": "solution-1"
        },
        {
            "first": "riddle-2",
            "second": "solution-2"
        },
        {
            "first": "riddle-3",
            "second": "solution-3"
        },
        {
            "first": "riddle-4",
            "second": "solution-4"
        }
    ]
}
```
In the final version, we obviously have secured this endpoint to avoid the contestant to see the solutions ;)
 

## The contestant enrollment feature
You're now going to implement the feature to enroll a new contestant, from the domain to the endpoint in the controller.

### Add Riddle data class
Data class concept in Kotlin is the best way to define simple objects, what we used to call Pojo in Java.
To create the Riddle data class :

- **Make a right click on the package _me.elgregos.escapecamp.game.domain.entity_**
- **Select _New/Kotlin Class/File_**
- **Write `Riddle` in the _Name_ field**
- **Select _Data class_**

![riddle-data-classe-creation.png](%234/riddle-data-classe-creation.png)

- **Add `name` field with type `String` field**
- **Add`assignedAt` and `solvedAt` fields with type `LocalDateTime`**

Your _Riddle.kt_ file should contain: 
```kotlin
package me.elgregos.escapecamp.game.domain.entity

import java.time.LocalDateTime

data class Riddle(
    val name: String,
    val assignedAt: LocalDateTime,
    val solvedAt: LocalDateTime? = null
)
```

### Add Contestant data class
Following the previous steps, you need to
- **Create a `Contestant` data class in the package _me.elgregos.escapecamp.game.domain.entity_**
- **Add `id` field with type `UUID`**
- **Add `name` field with type `String`**
- **Add `riddles` field with type `List<Riddle>`**
- **Initialize `riddles` with an empty list**
```kotlin
package me.elgregos.escapecamp.game.domain.entity

import java.util.*

data class Contestant(
    val id: UUID,
    val name: String,
    val riddles: List<Riddle> = listOf()
)
```

### Enroll contestants to the game
- **Open the `Game` entity**
- **Add `contestants` field of type `List<Contestant>**
- **Initialize the `contestants` as an empty list**
```kotlin
data class Game(
    override val id: UUID,
    override val version: Int = 1,
    override val createdAt: LocalDateTime,
    override val createdBy: UUID,
    override val updatedAt: LocalDateTime = createdAt,
    override val updatedBy: UUID = createdBy,
    val riddles: List<Pair<String, String>>,
    val contestants: List<Contestant> = listOf()) {}
```
- **Create an `enrollContestant()` function to the `Game` data class**
- **Add `contestant` parameter with type `Contestant`**
- **Add `enrolledAt` parameter with type `LocalDateTime`**
- **Use the `copy()` function of the data class to create an updated instance of the `Game`**
```kotlin
 fun enrollContestant(contestant: Contestant, enrolledAt: LocalDateTime) =
        copy(
            version = version + 1,
            updatedAt = enrolledAt,
            updatedBy = contestant.id,
            contestants = contestants.toMutableList().also { it.add(contestant) }
        )
```
You have implemented the logic to enroll a contestant to a Game.

### Create ContestantEnrolled event
Next step will be to create a new event based on GameCreated event
- **Open the `GameEvent` sealed class in the package `me.elgregos.escapecamp.game.domain.event`**
- **Create the `ContestEnrolled` data class which extends `GameEvent`**
```kotlin
 data class ContestantEnrolled(
        override val id: UUID = UUID.randomUUID(),
        override val sequenceNum: Long? = null,
        override val version: Int = 1,
        val enrolledAt: LocalDateTime = nowUTC(),
        val enrolledBy: UUID,
        val gameId: UUID,
        override val event: JsonNode,
    ) : GameEvent(
        id,
        sequenceNum,
        version,
        enrolledAt,
        enrolledBy,
        gameId,
        ContestantEnrolled::class.simpleName!!,
        event
    ) {}
```
- **Add a secondary constructor to `ContestantEnrolled`**
```kotlin
constructor(gameId: UUID, version: Int, enrolledBy: UUID, enrolledAt: LocalDateTime, contestants: List<Contestant>) : this(
    gameId = gameId,
    version = version,
    enrolledBy = enrolledBy,
    enrolledAt = enrolledAt,
    event = genericObjectMapper.createObjectNode()
        .put("id", "$gameId")
        .put("updatedBy", "$enrolledBy")
        .put("updatedAt", "$enrolledAt")
        .set("contestants", genericObjectMapper.valueToTree(contestants)))
```
Note that the event contains only the fields that we create or update. You'll understand soon why.

### Correct the compilation error in `GameProjectionSubscriber`
If you try to build the project now, you should notice a compilation error in the `GameProjectionSubscriber` class.
This is due to the missing branch in the `when` statement.
To correct this
- **Open the `GameProjectionSubscriber` class in package `me.elgregos.escapecamp.game.infrastructure.projection`**
- **Create an `updateGame()` function**
```kotlin
private fun updateGame(event: GameEvent) =
    gameProjectionStore.find(event.aggregateId)
        .flatMap { gameProjectionStore.update(mergeJsonPatch(it, event)) }
```
Here we use the `mergeJsonPatch()` generic function based on [Json Merge Patch](https://github.com/java-json-tools/json-patch/tree/master#json-merge-patch) which is designed to merge to Json documents.
- **Add the `ContestantEnrolled` branch that call this function**
```kotlin
when (it) {
    is GameCreated -> createGame(it)
    is ContestantEnrolled ->updateGame(it)
}
```

### Implement the business logic of contestant enrollment in `GameAggregate`
The  `GameAggregate` should now be completed to generate the `ContestantEnrollment` event.
- **Open the `GameAggregate` class in the package `me.elgregos.escapecamp.game.domain.event`**
- **Add an `enrollmentContest` function that takes two parameters** 
  - **contestant: Contestant**
  - **enrolledAt: LocalDateTime**
- **The function should return a `Flux`of `GameEvent`**
```kotlin
fun enrollContestant(contestant: Contestant, enrolledAt: LocalDateTime): Flux<GameEvent> =
```
- **Use the `previousState()` function to retrieve the reconstituted state from events stored in the database**
- **Map the `JsonNode` result to a `Game` object
```kotlin
fun enrollContestant(contestant: Contestant, enrolledAt: LocalDateTime): Flux<GameEvent> =
  previousState()
    .map { JsonConvertible.fromJson<Game>(it) }
```
- **Enroll the contestant to the game using the previously coded function in `Game` data class**
```kotlin
fun enrollContestant(contestant: Contestant, enrolledAt: LocalDateTime): Flux<GameEvent> =
  previousState()
  .map { JsonConvertible.fromJson<Game>(it) }
  .map { game -> game.enrollContestant(contestant, enrolledAt) }
```
- **In a `flatMapMany` closure**
  - **Call the `nextVersion()` function to be able to increase the version of the game after contestant enrollment**
  - **Map the `Mono` containing the version to a new `ContestantEnrollment` event**
```kotlin
fun enrollContestant(contestant: Contestant, enrolledAt: LocalDateTime): Flux<GameEvent> =
    previousState()
        .map { JsonConvertible.fromJson<Game>(it) }
        .map { game -> game.enrollContestant(contestant, enrolledAt) }
        .flatMapMany { game ->
            nextVersion().map { version ->
                GameEvent.ContestantEnrolled(gameId, version, userId, enrolledAt, game.contestants)
            }
        }
```
Congratulations, you've implemented your first business function in the `GameAggregate`

### Add command for contestant enrollment
Now that the application is able to produce `ContestantEnrolled` events, we need to trigger this by an action, also called Command in the Event Sourcing pattern.
A command is an infinitive verb representing the action.

To create the `EnrollContestant` command
- **Open the `GameCommand` sealed class in the package `me.elgregos.escapecamp.game.application`**
- **Add an `EnrollContestant` data class that extends `GameCommand`**
- **Add `gameId` field with type `UUID` and a random UUID as default value**
- **Add `enrolledBy`field with type `UUID`**
- **Add `enrolledAt` field with type `LocalDateTime` and initialized with `nowUTC()`**
- **Add `name` field with type `String`**
- **Add `contestant` field with type `Contestant` and initialize with a new instance**
```kotlin
    data class EnrollContestant(
        override val gameId: UUID,
        val enrolledAt: LocalDateTime = nowUTC(),
        val enrolledBy: UUID = UUID.randomUUID(),
        val name: String,
        val contestant: Contestant = Contestant(enrolledBy, name)
    ) : GameCommand(gameId)
```

### Add endpoint for contestant enrollment

### Add business logic for Game not found


