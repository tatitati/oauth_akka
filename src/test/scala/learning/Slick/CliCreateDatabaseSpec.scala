package test.learning.Slick

import app.infrastructure.user.{User, UserSchema}
import org.scalatest.FunSuite
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery
import scala.concurrent.duration._

import scala.concurrent.Await


class CliCreateDatabaseSpec extends FunSuite with Exec {
  val userTable = TableQuery[UserSchema]


//  test("Statements represent the SQL query CREATE TABLE") {
//    val db = Database.forConfig("mydb")
//
//    val result = DBIO.seq(
//      userTable.schema.create
//    )
//
//    val setupFuture = db.run(result)
//    Await.result(setupFuture, 2.second)
//  }

  test("SQL files to create database (migrations) are found") {
    assert(userTable.schema.create.statements === Vector("create table `user` (`first_name` TEXT NOT NULL,`last_name` TEXT NOT NULL,`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY)"))
  }

}