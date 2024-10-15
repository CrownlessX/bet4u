package com.epam.bet4u.ping

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/** Simple test controller. */
@RestController
class PingController {

  /** Healthcheck. */
  @GetMapping("/ping")
  fun ping() = "Pong"
}