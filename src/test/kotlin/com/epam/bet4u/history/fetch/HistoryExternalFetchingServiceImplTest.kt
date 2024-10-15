package com.epam.bet4u.history.fetch

import com.epam.bet4u.history.fetch.HistoryExternalFetchingServiceImpl.Companion.URL_RESULTS_2000
import com.epam.bet4u.history.fetch.HistoryExternalFetchingServiceImpl.Companion.URL_RESULTS_2001
import com.epam.bet4u.history.model.Match
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RestClientTest(HistoryExternalFetchingServiceImpl::class)
class HistoryExternalFetchingServiceImplTest {

  @Autowired
  private lateinit var mockRestServiceServer: MockRestServiceServer

  @Autowired
  private lateinit var gson: Gson

  @Autowired
  private lateinit var historyExternalFetchingService: HistoryExternalFetchingServiceImpl

  @Test
  fun fetchData_externalSourcesCalled_returnsMatchesFromAllSources() {
    mockRestServiceServer.expect(requestTo(URL_RESULTS_2000)).andRespond(
      withSuccess(gson.toJson(listOf(match1)), MediaType.APPLICATION_JSON)
    )
    mockRestServiceServer.expect(requestTo(URL_RESULTS_2001)).andRespond(
      withSuccess(gson.toJson(listOf(match2)), MediaType.APPLICATION_JSON)
    )

    val result = historyExternalFetchingService.fetchData()

    assertThat(result).containsExactly(match1, match2)
  }

  @Test
  fun fetchData_oneOfUrlsNotFound_returnsMatchesFromOtherSources() {
    mockRestServiceServer.expect(requestTo(URL_RESULTS_2000)).andRespond(
      withSuccess(gson.toJson(listOf(match1)), MediaType.APPLICATION_JSON)
    )
    mockRestServiceServer.expect(requestTo(URL_RESULTS_2001)).andRespond(
      withResourceNotFound()
    )

    val result = historyExternalFetchingService.fetchData()

    assertThat(result).containsExactly(match1)
  }

  private companion object {
    val match1 = Match(
      homeTeam = "homeTeam1",
      awayTeam = "awayTeam1",
      homeScore = 3,
      awayScore = 0,
      tournament = "Tournament 1",
      city = "Some city 1",
      country = "Magic Country"
    )
    val match2 = Match(
      homeTeam = "homeTeam2",
      awayTeam = "awayTeam2",
      homeScore = 1,
      awayScore = 4,
      tournament = "Tournament 2",
      city = "Some city 2",
      country = "Magic Country"
    )
  }
}