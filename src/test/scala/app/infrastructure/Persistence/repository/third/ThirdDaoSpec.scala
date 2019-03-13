package app.infrastructure.Persistence.repository.third

import app.infrastructure.Persistence.Exec
import app.infrastructure.Persistence.repository.third.ThirdDao.{exec, thirdSchema}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery
import test.builders.infrastructure.BuildThirdPersistedModel

class ThirdDaoSpec extends FunSuite with BeforeAndAfterEach with Exec {
  val thirdSchema = TableQuery[ThirdSchema]
  implicit val db = Database.forConfig("mydb")

  test("I can insert a new third") {
    ThirdDao.save(
      BuildThirdPersistedModel.anyNoPersisted(
        withName = "my row"
      )
    )

    val rows = exec(thirdSchema.result)

    assert(rows.size === 1)
    assert(rows.isInstanceOf[Vector[_]])
    assert(rows.head.isInstanceOf[ThirdPersistedModel])
    assert(rows.head.name === "my row")
    assert(rows.head.id.isInstanceOf[Some[_]])
  }

  test("I understand how to filter") {
    val query = thirdSchema.filter(_.name === "something").result.statements.mkString
    assert(query === "select `id`, `name`, `callback`, `homepage`, `description`, `client_id`, `client_secret` from `third` where `name` = 'something'")
  }

  test("I can read") {
    ThirdDao.save(
      BuildThirdPersistedModel.anyNoPersisted(withName = "my row")
    )

    val rows = ThirdDao.read(byname = "my row")

    assert(rows.size === 1)
    assert(rows.isInstanceOf[Vector[_]])
    assert(rows.head.isInstanceOf[ThirdPersistedModel])
    assert(rows.head.name === "my row")
    assert(rows.head.id.isInstanceOf[Some[_]])
  }

  override def beforeEach() {
    exec(thirdSchema.schema.dropIfExists)
    exec(thirdSchema.schema.create)
  }

  override def afterEach() {
    exec(thirdSchema.schema.drop)
  }



}