package me.elgregos.escapecamp.game.domain.event

import com.fasterxml.jackson.databind.JsonNode
import me.elgregos.escapecamp.game.domain.entity.Contestant
import me.elgregos.reakteves.domain.event.Event
import me.elgregos.reakteves.libs.genericObjectMapper
import me.elgregos.reakteves.libs.nowUTC
import java.time.LocalDateTime
import java.util.*

sealed class GameEvent(
    id: UUID,
    sequenceNum: Long?,
    version: Int,
    createdAt: LocalDateTime,
    createdBy: UUID,
    aggregateId: UUID,
    eventType: String,
    event: JsonNode
) : Event<UUID, UUID>(
    id, sequenceNum, version, createdAt, createdBy, eventType, aggregateId, event
) {

    data class GameCreated(
        override val id: UUID = UUID.randomUUID(),
        override val sequenceNum: Long? = null,
        override val version: Int = 1,
        override val createdAt: LocalDateTime = nowUTC(),
        override val createdBy: UUID,
        val gameId: UUID,
        override val event: JsonNode
    ) : GameEvent(
        id,
        sequenceNum,
        version,
        createdAt,
        createdBy,
        gameId,
        GameCreated::class.simpleName!!,
        event
    ) {

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
    ) {

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
    }
}