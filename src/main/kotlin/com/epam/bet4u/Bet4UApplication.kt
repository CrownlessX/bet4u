package com.epam.bet4u

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/** Bet4UApplication entry point. */
@SpringBootApplication
class Bet4UApplication

/** Application runner. */
fun main(args: Array<String>) {
  runApplication<Bet4UApplication>(*args)
}
