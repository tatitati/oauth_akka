package app.domain.ownerstore

import builders.BuildThird
import builders.authorizes.{BuildAuth, BuildScope}
import org.scalatest.FunSuite

class AuthSpec extends FunSuite {

  test("Can show scope granted to third") {
    val givenAuth = BuildAuth.any(
      withThird = BuildThird.any(),
      withScope = BuildScope.onlySurname()
    )

    assert(givenAuth.canAccessToFirstname() === false, "=> Third should be able to access to firstname")
    assert(givenAuth.canAccessToSurname() === true, "=> Third should be able to access to surname")
  }

  test("Can show data about third") {
    val givenAuth = BuildAuth.any(
      withThird = BuildThird.any(
        withName = "CircleCI",
        withHomepage = "http://www.whatever.com",
        withDescxription = "any description"
      )
    )

    assert(givenAuth.name == "CircleCI")
    assert(givenAuth.homepage == "http://www.whatever.com")
    assert(givenAuth.description == "any description")
  }
}