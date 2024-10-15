package com.epam.bet4u.history.aggregator

import com.epam.bet4u.history.model.Match
import org.springframework.stereotype.Service

/** Implementation of [MatchAggregator] interface. */
@Service
class MatchAggregatorImpl : MatchAggregator {

  override fun aggregate(matches: List<Match>): AggregateResult {
    val totalScores = mutableMapOf<String, Int>()
    val totalReceived = mutableMapOf<String, Int>()
    val numberTeamMatches = mutableMapOf<String, Int>()
    val totalWins = mutableMapOf<String, Int>()

    for (match in matches) {
      numberTeamMatches.putOrIncrease(match.homeTeam, 1)
      numberTeamMatches.putOrIncrease(match.awayTeam, 1)

      totalScores.putOrIncrease(match.homeTeam, match.homeScore)
      totalScores.putOrIncrease(match.awayTeam, match.awayScore)

      totalReceived.putOrIncrease(match.homeTeam, match.awayScore)
      totalReceived.putOrIncrease(match.awayTeam, match.homeScore)

      when {
        match.homeScore > match.awayScore ->
          totalWins.putOrIncrease(match.homeTeam, 1)

        match.homeScore < match.awayScore ->
          totalWins.putOrIncrease(match.awayTeam, 1)
      }
    }

    val mostWin = totalWins.allMaxScored().toResultTeams()
    val mostScoredPerGame =
      totalScores.mapValues { (team, totalScore) -> totalScore.toDouble() / numberTeamMatches[team]!! }
        .allMaxScored().toResultTeams()
    val lessReceivedPerGame =
      totalReceived.mapValues { (team, totalReceived) -> totalReceived.toDouble() / numberTeamMatches[team]!! }
        .allMinReceived().toResultTeams()

    return AggregateResult(
      mostWin, mostScoredPerGame, lessReceivedPerGame
    )
  }

  private companion object {
    fun MutableMap<String, Int>.putOrIncrease(key: String, increaseAmount: Int) = this.apply {
      this[key] = (this[key] ?: 0) + increaseAmount
    }

    fun Map<String, Number>.allMaxScored(): List<Pair<String, Number>> {
      val maxValue = maxByOrNull { it.value.toDouble() }?.value ?: return emptyList()

      return filter { it.value == maxValue }.toList()
    }

    fun Map<String, Double>.allMinReceived(): List<Pair<String, Number>> {
      val minValue = minByOrNull { it.value }?.value ?: return emptyList()

      return filter { it.value == minValue }.toList()
    }

    fun List<Pair<String, Number>>.toResultTeams() =
      map { (team, amount) -> AggregateResult.TeamStat(team, amount.toDouble()) }
  }
}