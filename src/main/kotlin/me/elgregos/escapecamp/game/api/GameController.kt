package me.elgregos.escapecamp.game.api

import jakarta.validation.Valid
import me.elgregos.escapecamp.game.api.dto.ContestantCreationDTO
import me.elgregos.escapecamp.game.application.GameCommand
import me.elgregos.escapecamp.game.application.GameCommandHandler
import me.elgregos.escapecamp.game.application.GameProjectionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.kotlin.core.publisher.toMono
import java.util.*

@RestController
@RequestMapping(
    path = ["/api/games"]
)
class GameController(
    private val gameCommandHandler: GameCommandHandler,
    private val gameProjectionService: GameProjectionService,
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun games() = gameProjectionService.games()

    @GetMapping("{gameId}")
    @ResponseStatus(HttpStatus.OK)
    fun game(@PathVariable @Valid gameId: UUID) = gameProjectionService.game(gameId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createGame() =
        gameCommandHandler.handle(GameCommand.CreateGame(createdBy = UUID.randomUUID()))
            .toMono()
            .map { mapOf(Pair("gameId", it.aggregateId)) }

    @PostMapping("{gameId}/contestants")
    @ResponseStatus(HttpStatus.CREATED)
    fun enrollContestant(
        @PathVariable @Valid gameId: UUID,
        @RequestBody @Valid contestantCreationDTO: ContestantCreationDTO,
    ) =
        gameCommandHandler.handle(GameCommand.EnrollContestant(gameId = gameId, name = contestantCreationDTO.name))
            .toMono()
            .map { mapOf("contestantId" to "${it.createdBy}", "eventType" to it.eventType) }

}