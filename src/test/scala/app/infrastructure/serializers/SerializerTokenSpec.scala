package app.infrastructure.serializers

import app.domain.thirdstore.MementoToken
import org.scalatest.FunSuite

class SerializerTokenSpec extends FunSuite {

  test("can convert to json") {
    val givenMementoToken = new MementoToken(
      "47331985-9cd6-4632-b505-fceb476100a1",
      "bearer",
      "b89a1ccb-18c9-41d0-bebc-48634b151991",
      6000
    )

    val transformer = new SerializerToken(givenMementoToken)

    assert(
      transformer.toJson() ==="""{"access_token":"47331985-9cd6-4632-b505-fceb476100a1","refresh_token":"b89a1ccb-18c9-41d0-bebc-48634b151991","token_type":"bearer","expires":6000}"""
    )
  }
}