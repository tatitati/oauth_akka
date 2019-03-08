package test.app.domain.ownerstore

import app.domain.owner.{Auth, Auths}
import builders.authorizes.BuildAuths
import org.scalatest.FunSuite
import test.builders.authorizes.BuildAuth
import test.builders._

class OwnerSpec extends FunSuite {

  test("Has a list of authorizations ") {
    val givenUser = BuildOwner.any()

    assert(givenUser.listAuth.isInstanceOf[Auths])
    assert(givenUser.countThirds === 2)
  }

  test("Has an editable profile") {
    val givenUser = BuildOwner.any(
      withProfile = BuildOwnerProfile.any(
        withFirstname = "gutierrez"
      )
    )

    val profileUpdated = givenUser.profile.setFirstname("gutierrez")
    val ownerUpdated = givenUser.setProfile(profileUpdated)
    assert(ownerUpdated.profile.firstname === "gutierrez")
  }

  test("Can delete(revoke) a third from the list") {
    val givenUser = BuildOwner.any(
      withAuths = BuildAuths.withClientIds("anyclientid1", "anyclientid2")
    )

    assert(givenUser.find("anyclientid1") !== None)
    assert(givenUser.find("anyclientid2") !== None)

    val ownerUpdated = givenUser.revoke("anyclientid1")

    assert(ownerUpdated.find("anyclientid1") === None)
    assert(ownerUpdated.find("anyclientid2") !== None)
  }

  test("Can authorize a third (add to the list)") {
    val givenUser = BuildOwner.any()

    assert(givenUser.find("newthirdclientId") === None, "=> Initially shouldn't have this third in the list")
    assert(givenUser.countThirds() === 2)

    val ownerUpdated = givenUser.grant(
      BuildAuth.any(
          withThird = BuildThird.any(
            withCredentials = BuildThirdCredentials.any(
              withClientid = "newthirdclientId"
          )
        )
      )
    )

    assert(ownerUpdated.find("newthirdclientId") !== None, "=> After grant acces it should be in the lsit")
    assert(ownerUpdated.countThirds() === 3)
  }

  test("can provide info about a third in the list when found") { // this test is a mess

    val givenOwner = BuildOwner.any(
      withAuths = new Auths(Vector(
        BuildAuth.any(
            withThird = BuildThird.any(
              withCredentials = BuildThirdCredentials.any(
                withClientid = "clientid1"
              ),
              withThirdProfile = BuildThirdProfile.any(
                withName = "travis"
              )
            )
        ),
        BuildAuth.any(),
        BuildAuth.any(),
      )
    ))

    val auth1 = givenOwner.find("clientid1")

    assert(auth1.isInstanceOf[Some[Auth]] === true)
    assert(auth1 match {
        case Some(value) => value.thirdName === "travis"
        case _ => false
    })

    val auth2 = givenOwner.find("aaasadsfasdf")
    assert(auth2 === None )
  }
}