package tech.housemoran.realgood.api

import tech.housemoran.realgood.api.exceptions.RepositoryException
import tech.housemoran.realgood.models.InmateRow

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
trait InmateRepository {
  @throws(classOf[RepositoryException])
  def save(inmate: InmateRow): Unit

  @throws(classOf[RepositoryException])
  def delete(inmate: InmateRow): Unit

  @throws(classOf[RepositoryException])
  def getAll(filter: (InmateRow) => Boolean): List[InmateRow]

  @throws(classOf[RepositoryException])
  def getByName(name: String): List[InmateRow]

  @throws(classOf[RepositoryException])
  def getByJail(jail: String): List[InmateRow]
}
