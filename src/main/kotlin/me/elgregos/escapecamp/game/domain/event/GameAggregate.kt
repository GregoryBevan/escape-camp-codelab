package me.elgregos.escapecamp.game.domain.event

import me.elgregos.escapecamp.game.domain.event.GameEvent.*
import me.elgregos.reakteves.domain.event.EventStore
import me.elgregos.reakteves.domain.event.JsonAggregate
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*

class GameAggregate(
    private val gameId: UUID,
    private val userId: UUID,
    gameEventStore: EventStore<GameEvent, UUID, UUID>
) :
    JsonAggregate<GameEvent, UUID, UUID>(gameId, gameEventStore) {

    fun createGame(createdAt: LocalDateTime): Flux<GameEvent> =
        Flux.just(GameCreated(gameId, userId, createdAt))
}