package test.app.domain.thirdstore.third

import org.scalatest.FunSuite
import test.builders.{BuildThird, BuildThirdProfile}

class OnEditThirdProfileSpec extends FunSuite {

  test("can edit third basic profile") {
    val givenThird = BuildThird.any(
      withThirdProfile = BuildThirdProfile.any(
        withName = "first name"
      )
    )

    val updatedProfile = givenThird.profile.updateName("second name")
    val updatedThird = givenThird.updateProfile(updatedProfile)

    assert(updatedThird.profile.name === "second name")
  }
}