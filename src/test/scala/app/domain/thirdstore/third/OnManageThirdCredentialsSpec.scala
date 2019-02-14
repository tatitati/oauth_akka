package test.app.domain.thirdstore.third

import org.scalatest.FunSuite
import test.builders.{BuildThird, BuildThirdProfile}

class OnManageThirdCredentialsSpec extends FunSuite {

  test("can refresh third credentials if they are compromised") {
    val givenThird = BuildThird.any()
    val clientId = givenThird.profile.clientid
    val clientSecret = givenThird.profile.clientsecret

    givenThird.refreshCredentials()

    val newclientid = givenThird.profile.clientid
    val newclientSecret = givenThird.profile.clientsecret

    assert(clientId !== newclientid)
    assert(clientSecret !== newclientSecret)
  }

  test("can edit clientid or clientsecret") {
    val givenThird = BuildThird.any(
      withThirdProfile = BuildThirdProfile.any(
        withClientid = "clientid1"
      )
    )

    assert(givenThird.profile.clientid === "clientid1")
    givenThird.profile.name = "clientid2"
    assert(givenThird.profile.name === "clientid2")
  }
}
