package com.epam.bet4u.history.controller

import com.epam.bet4u.history.aggregator.AggregateResult
import com.epam.bet4u.history.aggregator.AggregateResult.TeamStat
import com.epam.bet4u.history.aggregator.MatchAggregator
import com.epam.bet4u.history.fetch.HistoryFetchingService
import com.epam.bet4u.history.model.Match
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.willReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HistoryControllerTest {

  @Autowired
  private lateinit var restTemplate: TestRestTemplate

  @MockBean
  private lateinit var historyFetchingService: HistoryFetchingService

  @MockBean
  private lateinit var matchAggregator: MatchAggregator

  @Test
  fun aggregate_fetchingServiceReturnMatches_returnsAggregatedResult() {
    given(historyFetchingService.fetchData()) willReturn { listOf(match1, match2) }
    given(matchAggregator.aggregate(listOf(match1, match2))) willReturn { aggregateResult }

    val result = restTemplate.getForObject<AggregateResult>("/aggregate")

    assertThat(result).isEqualTo(aggregateResult)
  }

  @Test
  fun aggregate_fetchingServiceReturnNoMatches_returnsEmptyAggregatedResult() {
    val emptyResult = AggregateResult(emptyList(), emptyList(), emptyList())
    given(historyFetchingService.fetchData()) willReturn { emptyList() }
    given(matchAggregator.aggregate(emptyList())) willReturn { emptyResult }

    val result = restTemplate.getForObject<AggregateResult>("/aggregate")

    assertThat(result).isEqualTo(emptyResult)
  }

  private companion object {
    const val TEAM_1 = "team1"
    const val TEAM_2 = "team2"

    val match1 = Match(
      homeTeam = TEAM_1,
      awayTeam = TEAM_2,
      homeScore = 3,
      awayScore = 0,
      tournament = "Tournament 1",
      city = "Some city 1",
      country = "Magic Country"
    )
    val match2 = Match(
      homeTeam = TEAM_2,
      awayTeam = TEAM_1,
      homeScore = 1,
      awayScore = 3,
      tournament = "Tournament 2",
      city = "Some city 2",
      country = "Magic Country"
    )

    val aggregateResult = AggregateResult(
      mostWin = listOf(TeamStat(TEAM_1, 2.0)),
      mostScoredPerGame = listOf(TeamStat(TEAM_1, 3.0)),
      lessReceivedPerGame = listOf(TeamStat(TEAM_1, 0.5))
    )
  }
}