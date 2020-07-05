package tech.housemoran.realgood.datalayer

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
object HibernateUtil {
  private val sessionFactory = buildSessionFactory

  private def buildSessionFactory: SessionFactory = {
    try {
      return new Configuration().configure.buildSessionFactory
    } catch {
      case ex: Throwable => {
        System.err.println("Initial SessionFactory creation failed. " + ex)
        throw new ExceptionInInitializerError(ex)
      }
    }
  }

  def getSessionFactory = sessionFactory

  def shutdown: Unit = {
    getSessionFactory.close
  }
}
