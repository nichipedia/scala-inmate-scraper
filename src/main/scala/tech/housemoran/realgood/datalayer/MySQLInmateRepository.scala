package tech.housemoran.realgood.datalayer

import org.apache.logging.log4j.LogManager
import org.hibernate.Session
import tech.housemoran.realgood.api.InmateRepository
import tech.housemoran.realgood.api.exceptions.RepositoryException

import scala.collection.JavaConverters._
import tech.housemoran.realgood.models.InmateRow

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
object MySQLInmateRepository extends InmateRepository {

  private val log = LogManager.getLogger(MySQLInmateRepository)

  override def save(inmate: InmateRow): Unit = {
    val session: Session = HibernateUtil.getSessionFactory.openSession
    try {
      session.beginTransaction
      session.save(inmate)
      session.getTransaction.commit()
    } catch {
      case exception: Exception => log.error("Error with saving inmate: ", exception)
    } finally {
      session.close()
    }
  }

  override def delete(inmate: InmateRow): Unit = {
    val session: Session = HibernateUtil.getSessionFactory.openSession
    val id = inmate.id
    try {
      val query = session.createQuery("delete tech.housemoran.realgood.models.InmateRow where id=:id")
      query.setParameter("id", id)
      val results = query.executeUpdate()
      if (results == 0) {
        log.warn("Nothing was actually deleted!")
      } else {
        log.warn(s"Inmate with id $id was successfully deleted!")
      }
    } catch {
      case exception: Exception => {
        log.warn("Issue getting inmates: ", exception)
      }
    } finally {
      session.close()
    }
  }

  override def getAll(f: InmateRow => (Boolean)): List[InmateRow] = {
    val session: Session = HibernateUtil.getSessionFactory.openSession
    try {
      val query = session.createQuery("from tech.housemoran.realgood.models.InmateRow")
      query
        .getResultList
        .asScala
        .toList
        .map(_.asInstanceOf[InmateRow])
        .filter(f)
    } catch {
      case exception: Exception => {
        log.warn("Issue getting inmates: ", exception)
        List.empty
      }
    } finally {
      session.close()
    }
  }

  override def getByName(name: String): List[InmateRow] = {
    val session: Session = HibernateUtil.getSessionFactory.openSession
    try {
      val query = session.createQuery("from tech.housemoran.realgood.models.InmateRow where name like '%:name%'")
      query
        .setParameter("name", name)
        .getResultList
        .asScala
        .map(_.asInstanceOf[InmateRow])
        .toList
    } catch {
      case exception: Exception => {
        log.warn(s"Issue querying for inmate containing $name", exception)
        List.empty
      }
    } finally {
      session.close()
    }
  }

  override def getByJail(jail: String): List[InmateRow] = {
    val session: Session = HibernateUtil.getSessionFactory.openSession
    try {
      val query = session.createQuery("from tech.housemoran.realgood.models.InmateRow where jail like '%:jail%'")
      query
        .setParameter("jail", jail)
        .getResultList
        .asScala
        .map(_.asInstanceOf[InmateRow])
        .toList
    } catch {
      case exception: Exception => {
        log.warn(s"Issue querying for inmate containing $jail", exception)
        List.empty
      }
    } finally {
      session.close()
    }
  }
}