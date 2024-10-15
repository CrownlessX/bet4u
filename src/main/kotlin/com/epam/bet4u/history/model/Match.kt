package com.epam.bet4u.history.model

/** Basic historical match information. */
data class Match(
  val homeTeam: String,
  val awayTeam: String,
  val homeScore: Int,
  val awayScore: Int,
  val tournament: String,
  val city: String,
  val country: String
)