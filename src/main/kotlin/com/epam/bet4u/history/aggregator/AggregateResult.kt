package com.epam.bet4u.history.aggregator

/**
 * Information about top teams in specific categories
 */
data class AggregateResult(
  val mostWin: List<TeamStat>,
  val mostScoredPerGame: List<TeamStat>,
  val lessReceivedPerGame: List<TeamStat>
) {

  /**  Basic team statistic for category. */
  data class TeamStat(val team: String, val amount: Double)
}
