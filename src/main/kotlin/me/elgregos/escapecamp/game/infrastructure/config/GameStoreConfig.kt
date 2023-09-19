package me.elgregos.escapecamp.game.infrastructure.config

import me.elgregos.escapecamp.game.domain.entity.Game
import me.elgregos.escapecamp.game.domain.event.GameEvent
import me.elgregos.escapecamp.game.domain.event.GameEventRepository
import me.elgregos.escapecamp.game.infrastructure.event.GameEventEntity
import me.elgregos.escapecamp.game.infrastructure.projection.GameEntity
import me.elgregos.escapecamp.game.infrastructure.projection.GameProjectionRepository
import me.elgregos.reakteves.domain.event.EventStore
import me.elgregos.reakteves.domain.projection.ProjectionStore
import me.elgregos.reakteves.infrastructure.event.DefaultEventStore
import me.elgregos.reakteves.infrastructure.projection.DefaultProjectionStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.UUID

@Configuration
class GameStoreConfig {

    @Bean
    fun gameEventStore(gameEventRepository: GameEventRepository): EventStore<GameEvent, UUID, UUID> =
        DefaultEventStore(gameEventRepository, GameEventEntity::class, GameEvent::class)

    @Bean
    fun gameProjectionStore(gameProjectionRepository: GameProjectionRepository): ProjectionStore<Game, UUID, UUID> =
        DefaultProjectionStore(gameProjectionRepository, GameEntity::class, Game::class)
}