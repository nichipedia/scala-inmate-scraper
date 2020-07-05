package tech.housemoran.realgood.states.la

import tech.housemoran.realgood.api.JailScraper
import org.apache.logging.log4j.LogManager
import java.util.Date
import java.text.SimpleDateFormat
import java.net.URL
import org.jsoup.Jsoup
import tech.housemoran.realgood.models.InmateRow
import tech.housemoran.realgood.models.protobuf.InmateData.Inmate
import scala.collection.JavaConverters._

class StTammanyScraper extends JailScraper {

  private val log = LogManager.getLogger(classOf[StTammanyScraper])
  private val jail = "St. Tammany Parish Jail"

  def getRecentInmates: Iterable[InmateRow] = {
    val date = new Date
    val formatter = new SimpleDateFormat("MM/dd/YY")
    val today = formatter.format(date)
    log.info(today)
    this.log.info("Collecting Recent Inmates from {}", this.jail)
    var inmates = List[Inmate]()
    for (i <- 26 to 35) {
      val mainUrl = getPagiURL(i)
      val resp = Jsoup.connect(mainUrl.toString).execute
      val document = Jsoup.parse(resp.body)
      val recentInmates = document.select("div[class=inmate row]").iterator().asScala
        .toStream
        .filter(inmateRow => inmateRow.select("td").get(1).text.equals(today))
        .map(f = inmateRow => {
          val fullName = inmateRow.select("h3").stream().findFirst().get().text
          val partialImg = inmateRow.select("img").stream().findFirst().get().attr("src")
          val bookingDate = inmateRow.select("td").get(1).text
          val bail = inmateRow.select("b").first().text
          val description = inmateRow.select("td").get(3).text
          val inmateNumber = inmateRow.select("td").get(0).text
          val builder = Inmate.newBuilder
          val profileURL = getProfileURL(inmateNumber)
          val profile = Jsoup.parse(profileURL, 20000)
          val rows = profile.select("td")
          val age = rows.get(1).text()
          if (age.length != 0) {
            builder.setAge(age)
          } else {
            builder.setAge("NOT FOUND")
          }
          var sex = rows.get(2).text
          if (sex.equals("M")) {
            builder.setGender(Inmate.Gender.MALE);
          } else if (sex.equals("F")) {
            builder.setGender(Inmate.Gender.FEMALE);
          } else {
            builder.setGender(Inmate.Gender.UNKNOWN);
          }
          val race = rows.get(3).text
          if (race.length != 0) {
            builder.setRace(race)
          } else {
            builder.setRace("NOT FOUND")
          }
          val releasable = rows.get(5).text
          if (releasable.length != 0) {
            builder.setReleaseable(releasable);
          } else {
            builder.setReleaseable("NOT FOUND")
          }
          val mugShotURL = getMugShotURL(inmateNumber)
          builder
            .setName(fullName)
            .setBookingDescription(description)
            .setJail(jail)
            .setDate(today)
            .setBail(bail)
            .setMugShot(mugShotURL)
          builder.build
        })
        .toList
      inmates = inmates++recentInmates
    }
    val inmateData = inmates.map(_ => new InmateRow)
    return inmateData
  }

  def getJailName: String = {
    return this.jail
  }

  private def getPagiURL(pageNumber: Int): URL = {
    return new URL("https://www.stpso.com//cron/inmate_pull.php?page=current&page_num=%d".format(pageNumber))
  }

  private def getMugShotURL(inmateId: String): String = {
    return "https://www.stpso.com/inmate_roster_gallery/%s.jpg".format(inmateId)
  }


  private def getProfileURL(inmateId: String): URL = {
    return new URL("https://www.stpso.com//cron/inmate_pull.php?page=single&booking_number=%s".format(inmateId))
  }

}