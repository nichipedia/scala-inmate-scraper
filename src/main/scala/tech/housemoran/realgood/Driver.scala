package tech.housemoran.realgood

import org.hibernate.Session
import tech.housemoran.realgood.datalayer.HibernateUtil
import tech.housemoran.realgood.models.InmateRow
import tech.housemoran.realgood.states.la.StTammanyScraper

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
object Driver extends App {
  override def main(args: Array[String]) {
    val session: Session = HibernateUtil.getSessionFactory.openSession
    val scraper = new StTammanyScraper
    scraper.getRecentInmates.foreach(inmateRow => {
      session.beginTransaction
      session.save(inmateRow)
      session.getTransaction.commit()
    })
    println("Finished!")
  }
}