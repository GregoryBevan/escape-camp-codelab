package me.elgregos.escapecamp.game.application

import me.elgregos.escapecamp.game.domain.entity.Contestant
import me.elgregos.reakteves.application.Command
import me.elgregos.reakteves.libs.nowUTC
import java.time.LocalDateTime
import java.util.*

sealed class GameCommand(open val gameId: UUID) : Command {

    data class CreateGame(
        override val gameId: UUID = UUID.randomUUID(),
        val createdBy: UUID,
        val createdAt: LocalDateTime = nowUTC()
    ) : GameCommand(gameId)

    data class EnrollContestant(
        override val gameId: UUID,
        val enrolledAt: LocalDateTime = nowUTC(),
        val enrolledBy: UUID = UUID.randomUUID(),
        val name: String,
        val contestant: Contestant = Contestant(enrolledBy, name)
    ) : GameCommand(gameId)

}