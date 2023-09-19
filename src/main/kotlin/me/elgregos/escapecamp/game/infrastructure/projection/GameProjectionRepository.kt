package me.elgregos.escapecamp.game.infrastructure.projection

import me.elgregos.escapecamp.game.domain.entity.Game
import me.elgregos.reakteves.infrastructure.projection.ProjectionRepository
import java.util.*

interface GameProjectionRepository : ProjectionRepository<GameEntity, Game, UUID, UUID>