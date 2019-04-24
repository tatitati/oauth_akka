package app.domain.services

import java.util.UUID

import com.github.nscala_time.time.Imports._
import app.domain.code.Code
import app.domain.model.thirdapp.ThirdappId
import app.domain.model.auth.{Auth, AuthId, AuthToken}
import app.domain.model.user.UserId

object MapCodeToAuth {
  def toAuth(code: Code): Auth = {
    new Auth(
      authId = AuthId(UUID.randomUUID()),
      thirdappId = ThirdappId(code.thirdappId.value),
      userId = UserId(code.userId.value),
      scope = code.scope,
      token = generateToken()
    )
  }

  private def generateToken(): AuthToken = {
    AuthToken(
      accessToken = UUID.randomUUID(),
      refreshToken = UUID.randomUUID(),
      generatedIn = DateTime.now(),
      tokenType = "bearer",
      expiresIn = 36000
    )
  }
}