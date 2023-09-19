package me.elgregos.escapecamp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EscapeCampApplication

fun main(args: Array<String>) {
	runApplication<EscapeCampApplication>(*args)
}
