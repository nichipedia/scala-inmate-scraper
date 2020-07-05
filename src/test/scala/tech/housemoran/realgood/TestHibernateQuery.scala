package tech.housemoran.realgood

import org.junit.Test
import org.hibernate.Session
import tech.housemoran.realgood.datalayer.{HibernateUtil, MySQLInmateRepository}

import scala.collection.JavaConverters._

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
class TestHibernateQuery {



  @Test
  def simpleGet(): Unit = {
    val session: Session = HibernateUtil.getSessionFactory.openSession
    val query = session.createQuery("from tech.housemoran.realgood.models.InmateRow where id>3")
    val results = query.getResultList.asScala
    assert(results.nonEmpty)
  }

  @Test
  def testRepositoryObjectGet(): Unit = {
    val results = MySQLInmateRepository.getAll(inmate => inmate.id > 3)
    results.foreach(println)
    assert(results.nonEmpty)
  }
}
