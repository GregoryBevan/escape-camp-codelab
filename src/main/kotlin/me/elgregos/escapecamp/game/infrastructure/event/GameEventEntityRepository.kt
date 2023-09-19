package me.elgregos.escapecamp.game.infrastructure.event

import me.elgregos.escapecamp.game.domain.event.GameEvent
import me.elgregos.escapecamp.game.domain.event.GameEventRepository
import me.elgregos.reakteves.infrastructure.event.EventEntityRepository
import java.util.*

interface GameEventEntityRepository : EventEntityRepository<GameEventEntity, GameEvent, UUID, UUID>, GameEventRepository