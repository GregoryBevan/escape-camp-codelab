package me.elgregos.escapecamp.game.infrastructure.event

import com.fasterxml.jackson.databind.JsonNode
import me.elgregos.escapecamp.game.domain.event.GameEvent
import me.elgregos.reakteves.infrastructure.event.EventEntity
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("game_event")
data class GameEventEntity(
    @get:JvmName("id") val id: UUID,
    override val sequenceNum: Long? = null,
    override val version: Int = 1,
    override val createdAt: LocalDateTime,
    override val createdBy: UUID,
    override val eventType: String,
    override val aggregateId: UUID = UUID.randomUUID(),
    override val event: JsonNode
) : EventEntity<GameEvent, UUID, UUID>(
    id,
    sequenceNum,
    version,
    createdAt,
    createdBy,
    eventType,
    aggregateId,
    event
)