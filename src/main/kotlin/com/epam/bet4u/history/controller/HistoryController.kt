package com.epam.bet4u.history.controller

import com.epam.bet4u.history.aggregator.AggregateResult
import com.epam.bet4u.history.aggregator.MatchAggregator
import com.epam.bet4u.history.fetch.HistoryFetchingService
import com.epam.bet4u.history.model.Match
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/** Exposes endpoints for history records. */
@RestController
class HistoryController constructor(
  private val historyFetchingService: HistoryFetchingService,
  private val matchAggregator: MatchAggregator,
) {

  /** Aggregate information from [Match]es and returns [AggregateResult]. */
  @GetMapping("/aggregate")
  fun aggregate(): AggregateResult {
    val data = historyFetchingService.fetchData()

    return matchAggregator.aggregate(data)
  }
}