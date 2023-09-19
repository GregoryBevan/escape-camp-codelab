package me.elgregos.escapecamp.game.api.dto

import jakarta.validation.constraints.NotBlank

data class ContestantCreationDTO(
    @NotBlank val name: String
)
