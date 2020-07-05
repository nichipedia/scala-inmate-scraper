package tech.housemoran.realgood.api

import tech.housemoran.realgood.models.InmateRow

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
trait JailScraper {
  def getRecentInmates: Iterable[InmateRow]
  def getJailName: String
}
