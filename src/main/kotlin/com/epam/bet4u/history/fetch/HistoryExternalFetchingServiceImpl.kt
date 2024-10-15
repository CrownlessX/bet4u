package com.epam.bet4u.history.fetch

import com.epam.bet4u.history.model.Match
import com.google.common.flogger.FluentLogger
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

/** Implementation of [HistoryFetchingService] that fetches data from external sources. */
@Service
class HistoryExternalFetchingServiceImpl(restClientBuilder: RestClient.Builder) :
  HistoryFetchingService {

  private val restClient = restClientBuilder.build()

  override fun fetchData() =
    listOf(URL_RESULTS_2000, URL_RESULTS_2001).flatMap { fetch(it) }

  private fun fetch(url: String): List<Match> = try {
    restClient.get().uri(url).retrieve().body() ?: emptyList()
  } catch (e: HttpClientErrorException) {
    logger.atSevere().withCause(e).log("Error during fetching information")

    emptyList()
  }

  internal companion object {
    const val URL_RESULTS_2000 =
      "https://jnfue27hgezyex5k7aniuvzqiu0hshdq.lambda-url.eu-central-1.on.aws/soccer/2000"
    const val URL_RESULTS_2001 =
      "https://jnfue27hgezyex5k7aniuvzqiu0hshdq.lambda-url.eu-central-1.on.aws/soccer/2001"

    private val logger: FluentLogger = FluentLogger.forEnclosingClass()
  }
}
