package com.epam.bet4u.history.fetch

import com.epam.bet4u.history.model.Match

/** Interface for services that responsible for fetching historical information about [Match]es. */
interface HistoryFetchingService {

  /** Fetch [Match] data from a source. */
  fun fetchData(): List<Match>
}