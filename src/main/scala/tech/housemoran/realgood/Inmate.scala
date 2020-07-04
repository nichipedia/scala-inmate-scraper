package tech.housemoran.realgood
import javax.persistence._

/** ************************************************
 * * Created by Nicholas on 7/4/2020.               **
 * *************************************************/
@Entity
@Table(name = "INMATES")
class Inmate(name: String) extends Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="ID", unique = true, nullable = false)
  private var id: Int = _

  @Column(name="NAME", unique = false, nullable = false)
  private var status = name

}
