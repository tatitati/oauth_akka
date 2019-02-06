package app.domain.ownerstore

import builders.{BuildThird, BuildThirdProfile}
import builders.authorizes.{BuildAuth, BuildListAuth, BuildScope}
import org.scalatest.FunSuite

class ListAuthSpec extends FunSuite {
  test("Builder can create a list of AppPermissions") {
    assert(BuildListAuth.any().count === 2)
  }

  test("Builder can create a list of permissions with custom ids") {
    val listmap = BuildListAuth.anyListWithClientIds(List("clientid1", "clientid2", "clientid3"))
    assert(listmap.count === 3)

  }

  test("Can know if exist an item in list") {
    val listmap = BuildListAuth.any()

    assert(listmap.existThird("anyclientid") === true)
    assert(listmap.existThird("clientId2") === false)
  }

  test("can find item by clientid") {

    val givenAuth1 = BuildAuth.any(
      withThird = BuildThird.any(
        withThirdProfile = BuildThirdProfile.any(
          withClientid = "clientid1"
        )
      ),
      withScope = BuildScope.onlyEmailAndFirstname()
    )

    val givenAuth2 = BuildAuth.any(
      withThird = BuildThird.any(
        withThirdProfile = BuildThirdProfile.any(
          withClientid = "clientid2"
        )
      ),
      withScope = BuildScope.onlySurname()
    )

    val givenAuthList = new ListAuth(List(givenAuth1, givenAuth2))

    val scope1 = givenAuthList.find("clientid1")
    assert(scope1.isInstanceOf[Some[Auth]] === true)

    val scope2 = givenAuthList.find("aaaaaaaa")
    assert(scope2 === None)
  }

  test("Can remove from list") {
    val listmap = BuildListAuth.anyListWithClientIds(List("clientid1", "clientid2", "clientid3"))

    assert(listmap.existThird("clientid1") === true)
    assert(listmap.existThird("clientid2") === true)
    assert(listmap.existThird("clientid3") === true)
    listmap.removeThird("clientid2")
    assert(listmap.existThird("clientid1") === true)
    assert(listmap.existThird("clientid2") === false)
    assert(listmap.existThird("clientid3") === true)
  }

  test("Can add to list") {
    val listmap = BuildListAuth.anyListWithClientIds(List("clientid1", "clientid2"))

    val third = BuildAuth.anyAuthorizationWithClientId("clientid3")

    assert(listmap.existThird("clientid1") === true)
    assert(listmap.existThird("clientid2") === true)
    assert(listmap.existThird("clientid3") === false)
    listmap.addThird(third)
    assert(listmap.existThird("clientid1") === true)
    assert(listmap.existThird("clientid2") === true)
    assert(listmap.existThird("clientid3") === true)

    assert(listmap.count() === 3)
  }

  test("Cannot add twice the same to list") {
    val listmap = BuildListAuth.anyListWithClientIds(List("clientid1", "clientid2"))

    val third = BuildAuth.anyAuthorizationWithClientId("clientid3")

    assert(listmap.count() === 2)
    listmap.addThird(third)
    assert(listmap.count() === 3)
    listmap.addThird(third)
    assert(listmap.count() === 3)
  }
}
