package me.elgregos.escapecamp.game.api

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import me.elgregos.escapecamp.game.application.GameCommand
import me.elgregos.escapecamp.game.application.GameCommandHandler
import me.elgregos.escapecamp.game.application.GameProjectionService
import org.springframework.http.HttpStatus
import reactor.kotlin.core.publisher.toMono
import java.util.*

@RestController
@RequestMapping(
    path = ["/api/games"]
)
class GameController(
    private val gameCommandHandler: GameCommandHandler,
    private val gameProjectionService: GameProjectionService
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

}