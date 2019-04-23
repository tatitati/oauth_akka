package app.domain.model.auth

import java.util.UUID

import app.domain.model.thirdapp.SiteId
import app.domain.model.{IdentifiableInPersistence, Scope}
import app.domain.model.user.UserId
import com.github.nscala_time.time.Imports.DateTime

class Auth(
            val authId: AuthId,
            val siteId: SiteId,
            val userId: UserId,
            val scope: Scope,
            private var token: AuthToken
  ) extends IdentifiableInPersistence {

  def getToken: AuthToken = {
    token
  }

  def equals(auth: Auth): Boolean = {
    authId.equals(auth.authId)
  }

  def canReadFirstname: Boolean = token.isLive match {
    case true => scope.firstname
    case _ => false
  }

  def canReadSurname: Boolean = token.isLive match {
    case true => scope.surname
    case _ => false
  }

  def canReadEmail: Boolean = token.isLive match {
    case true => scope.email
    case _ => false
  }

  def refreshToken(refreshToken: UUID, grantType: String): Unit = {
    if(token.canBeRefreshed(refreshToken, grantType)) {
      token = AuthToken(
        accessToken = java.util.UUID.randomUUID,
        tokenType = "bearer",
        refreshToken = java.util.UUID.randomUUID,
        expiresIn = 60,
        generatedIn = DateTime.now()
      )
    }
  }
}
