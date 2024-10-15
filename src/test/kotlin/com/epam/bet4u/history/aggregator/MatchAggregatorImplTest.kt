package com.epam.bet4u.history.aggregator

import com.epam.bet4u.history.aggregator.AggregateResult.TeamStat
import com.epam.bet4u.history.model.Match
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

class MatchAggregatorImplTest {

  private lateinit var matchAggregator: MatchAggregatorImpl

  @BeforeEach
  fun setUp() {
    matchAggregator = MatchAggregatorImpl()
  }

  @Test
  fun aggregate_returnsAggregateResult() {
    val numberOfWins = 2.0 // Game 1 and 2
    val mostScorePerGame = 3.0 // (3 + 4 + 2) / 3
    val lessReceivedPerGame = 2.0 // Team 3 in game 3
    val expectedAggregateResult = AggregateResult(
      mostWin = listOf(TeamStat(TEAM_NAME_1, numberOfWins)),
      mostScoredPerGame = listOf(TeamStat(TEAM_NAME_1, mostScorePerGame)),
      lessReceivedPerGame = listOf(TeamStat(TEAM_NAME_3, lessReceivedPerGame))
    )
    val matches = listOf(matchTeam1Win, matchTeam2Win, matchTeam3Draw)

    val result = matchAggregator.aggregate(matches)

    assertThat(result).isEqualTo(expectedAggregateResult)
  }

  private companion object {
    const val TEAM_NAME_1 = "radiant"
    const val TEAM_NAME_2 = "dire"
    const val TEAM_NAME_3 = "neutral"

    val matchTeam1Win = Match(
      homeTeam = TEAM_NAME_1,
      awayTeam = TEAM_NAME_2,
      homeScore = 3,
      awayScore = 2,
      tournament = "Tournament 1",
      city = "Some city 1",
      country = "Magic Country"
    )
    val matchTeam2Win = Match(
      homeTeam = TEAM_NAME_2,
      awayTeam = TEAM_NAME_1,
      homeScore = 3,
      awayScore = 4,
      tournament = "Tournament 2",
      city = "Some city 2",
      country = "Not a Magic Country"
    )

    val matchTeam3Draw = Match(
      homeTeam = TEAM_NAME_1,
      awayTeam = TEAM_NAME_3,
      homeScore = 2,
      awayScore = 2,
      tournament = "Tournament 3",
      city = "Some city 3",
      country = "Magic Country"
    )
  }
}