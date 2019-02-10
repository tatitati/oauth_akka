package test.app.domain.thirdstore.Resource

import builders.BuildResource
import org.scalatest.FunSuite

class OnRevokeTokenSpec extends FunSuite{

  test("Can revoke resource token") {
    val resourceWithLiveToken = BuildResource.withLiveToken()

    resourceWithLiveToken.revoke()

    assert(resourceWithLiveToken.token === None)
  }

  test("Receives an exception if there is no token") {
    val resourceWithLiveToken = BuildResource.withoutToken()

    val thrown = intercept[IllegalAccessException] {
      resourceWithLiveToken.revoke()
    }
    assert(thrown.getMessage === "There is no token, so it cannot be revoked.")

  }
}
