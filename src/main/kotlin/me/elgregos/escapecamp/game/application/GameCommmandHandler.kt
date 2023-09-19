package me.elgregos.escapecamp.game.application

import me.elgregos.escapecamp.game.application.GameCommand.*
import me.elgregos.escapecamp.game.domain.event.GameAggregate
import me.elgregos.escapecamp.game.domain.event.GameEvent
import me.elgregos.reakteves.domain.event.EventStore
import me.elgregos.reakteves.infrastructure.event.ReactorEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameCommandHandler(
    val gameEventStore: EventStore<GameEvent, UUID, UUID>,
    val gameEventPublisher: ReactorEventPublisher<UUID, UUID>
) {

    fun handle(gameCommand: GameCommand) =
        when (gameCommand) {
            is CreateGame -> createGame(gameCommand)
        }
            .flatMap { gameEventStore.save(it) }
            .doOnNext { gameEventPublisher.publish(it) }

    private fun createGame(gameCommand: CreateGame) =
        GameAggregate(gameCommand.gameId, gameCommand.createdBy, gameEventStore)
            .createGame(gameCommand.createdAt)

}