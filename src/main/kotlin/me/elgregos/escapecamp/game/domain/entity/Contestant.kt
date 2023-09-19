package me.elgregos.escapecamp.game.domain.entity

import java.util.*

data class Contestant(
    val id: UUID,
    val name: String,
    val riddles: List<Riddle> = listOf()
)
