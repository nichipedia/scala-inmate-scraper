package tech.housemoran.realgood.models

import javax.persistence._
import tech.housemoran.realgood.models.protobuf.InmateData.Inmate

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
@Entity
@Table(name = "INMATES")
class InmateRow() extends Serializable {

  def this(inmate: Inmate) {
    this()
    this.name = inmate.getName
    this.mugShotUrl = inmate.getMugShot
    this.bail = inmate.getBail
    this.age = inmate.getAge
    this.bookingDescription = inmate.getBookingDescription
    this.date = inmate.getDate
    this.gender = inmate.getGender.toString
    this.jail = inmate.getJail
    this.race = inmate.getRace
    this.releaseable = inmate.getReleaseable
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="ID", unique = true, nullable = false)
  var id: Int = _

  @Column(name = "NAME", unique = false, nullable = false)
  var name: String = _

  @Column(name = "MUG_SHOT_URL", unique = false, nullable = false)
  var mugShotUrl: String = _

  @Column(name = "BAIL", unique = false, nullable = false)
  var bail: String = _

  @Column(name = "AGE", unique = false, nullable = false)
  var age: String = _

  @Column(name = "BOOKING_DESCRIPTION", unique = false, nullable = false)
  var bookingDescription: String = _

  @Column(name = "BOOKING_DATE", unique = false, nullable = false)
  var date: String = _

  @Column(name = "GENDER", unique = false, nullable = false)
  var gender: String = _

  @Column(name = "JAIL", unique = false, nullable = false)
  var jail: String = _

  @Column(name = "RACE", unique = false, nullable = false)
  var race: String = _

  @Column(name = "RELEASEABLE", unique = false, nullable = false)
  var releaseable: String = _

  override def toString: String = s"$id: $name"
}