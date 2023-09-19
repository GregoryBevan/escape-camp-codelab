package me.elgregos.escapecamp.game.application

import me.elgregos.escapecamp.game.domain.entity.Game
import me.elgregos.reakteves.domain.projection.ProjectionStore
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameProjectionService(
    private val gameProjectionStore: ProjectionStore<Game, UUID, UUID>) {

    fun games() =
        gameProjectionStore.list()

    fun game(gameId: UUID) =
        gameProjectionStore.find(gameId)
}