package me.elgregos.escapecamp.game.domain.entity

import me.elgregos.reakteves.domain.entity.DomainEntity
import java.time.LocalDateTime
import java.util.*

data class Game(
    override val id: UUID,
    override val version: Int = 1,
    override val createdAt: LocalDateTime,
    override val createdBy: UUID,
    override val updatedAt: LocalDateTime = createdAt,
    override val updatedBy: UUID = createdBy,
    val riddleSolutions: List<Pair<String, String>>,
    val contestants: List<Contestant> = listOf()
) : DomainEntity<UUID, UUID> {

    fun enrollContestant(contestant: Contestant, enrolledAt: LocalDateTime) =
        copy(
            version = version + 1,
            updatedAt = enrolledAt,
            updatedBy = contestant.id,
            contestants = contestants.toMutableList().also { it.add(contestant) }
        )
}