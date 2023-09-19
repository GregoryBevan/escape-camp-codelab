package me.elgregos.escapecamp.game.domain.event

import me.elgregos.escapecamp.game.domain.entity.Contestant
import me.elgregos.escapecamp.game.domain.entity.Game
import me.elgregos.escapecamp.game.domain.event.GameEvent.GameCreated
import me.elgregos.reakteves.domain.JsonConvertible
import me.elgregos.reakteves.domain.event.EventStore
import me.elgregos.reakteves.domain.event.JsonAggregate
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*

val riddleSolutions: List<Pair<String, String>> = listOf(
    "riddle-1" to "solution-1",
    "riddle-2" to "solution-2",
    "riddle-3" to "solution-3",
    "riddle-4" to "solution-4"
)

class GameAggregate(
    private val gameId: UUID,
    private val userId: UUID,
    gameEventStore: EventStore<GameEvent, UUID, UUID>,
) :
    JsonAggregate<GameEvent, UUID, UUID>(gameId, gameEventStore) {

    fun createGame(createdAt: LocalDateTime): Flux<GameEvent> =
        Flux.just(GameCreated(gameId, userId, createdAt, riddleSolutions))

    fun enrollContestant(contestant: Contestant, enrolledAt: LocalDateTime): Flux<GameEvent> =
        previousState()
            .map { JsonConvertible.fromJson<Game>(it) }
            .map { game -> game.enrollContestant(contestant, enrolledAt) }
            .flatMapMany { game ->
                nextVersion().map { version ->
                    GameEvent.ContestantEnrolled(gameId, version, userId, enrolledAt, game.contestants)
                }
            }

}