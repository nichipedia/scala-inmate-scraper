package tech.housemoran.realgood

import org.hibernate.Session

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
object Driver extends App {
  def main(args: Array[String]) {
    val session: Session = HibernateUtil.getSessionFactory.openSession

    session.beginTransaction
    val inmate = new Inmate("Mark")
    session.save(inmate)
    session.getTransaction.commit()
    println("Finished!")
  }
}