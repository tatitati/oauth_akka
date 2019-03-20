package test.app.infrastructure.Persistence.owner

import app.infrastructure.Persistence.owner.OwnerProfilePersistModel
import com.github.nscala_time.time.Imports.DateTime
import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class OwnerProfileSchema(tag: Tag) extends Table[OwnerProfilePersistModel](tag, "owner_profile") {

  import app.infrastructure.Persistence.CustomDateTimeToTimestamp._

  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def firstname = column[String]("firstname")
  def surname = column[String]("surname")
  def email = column[String]("email")
  def emailconfirmed = column[Boolean]("email_confirmed")
  def datebirth = column[DateTime]("datebirth", O.SqlType("DATETIME")) // this uses custom mapper type

  def * = (
    id,
    firstname,
    surname,
    email,
    emailconfirmed,
    datebirth
  ).mapTo[OwnerProfilePersistModel]
}