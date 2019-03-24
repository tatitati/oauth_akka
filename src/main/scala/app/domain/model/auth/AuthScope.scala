package app.domain.model.auth

import app.domain.model.IdentifiableInPersistence

case class AuthScope(
                  val firstname: Boolean,
                  val surname: Boolean,
                  val email: Boolean
  ) extends IdentifiableInPersistence {

  if(!firstname && !surname && !email) {
    throw new IllegalArgumentException("An scope that forbid everything doesnt make sense")
  }
}