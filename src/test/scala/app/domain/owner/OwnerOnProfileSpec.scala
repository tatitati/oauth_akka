package test.app.domain.ownerstore

import app.domain.owner.OwnerProfileMemento
import org.scalatest.FunSuite
import test.builders._

class OwnerOnProfileSpec extends FunSuite {

  test("Rreturn a memento of profile, no editable, to external world") {
    val givenUser = BuildOwner.any(
      withProfile = BuildOwnerProfile.any(
        withFirstname = "gutierrez"
      )
    )

    assert(givenUser.getOwnerProfile.isInstanceOf[OwnerProfileMemento])
  }

  test("Has an editable profile that only the owner can edit") {
    val givenUser = BuildOwner.any(
      withProfile = BuildOwnerProfile.any(
        withFirstname = "gutierrez"
      )
    )

    givenUser.setFirstname("new name")
    assert(givenUser.getOwnerProfile.firstname === "new name")
  }
}