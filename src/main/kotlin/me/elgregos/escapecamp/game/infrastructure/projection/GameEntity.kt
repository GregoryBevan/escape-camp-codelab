package me.elgregos.escapecamp.game.infrastructure.projection

import com.fasterxml.jackson.databind.JsonNode
import me.elgregos.escapecamp.game.domain.entity.Game
import me.elgregos.reakteves.infrastructure.projection.ProjectionEntity
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("game")
data class GameEntity(
    @get:JvmName("id") val id: UUID,
    override val sequenceNum: Long? = null,
    override val version: Int,
    override val createdAt: LocalDateTime,
    override val createdBy: UUID,
    override val updatedAt: LocalDateTime,
    override val updatedBy: UUID,
    override val details: JsonNode
) : ProjectionEntity<Game, UUID, UUID>(id, sequenceNum, version, createdAt, createdBy, updatedAt, updatedBy, details)