package com.epam.bet4u.history.aggregator

import com.epam.bet4u.history.model.Match

/** Aggregates [Match]es results.  */
interface MatchAggregator {

  /** Analyze List of provided [Match]es and return [AggregateResult]. */
  fun aggregate(matches: List<Match>): AggregateResult
}