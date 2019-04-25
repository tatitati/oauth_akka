package app.infrastructure.persistence.owner

import app.domain.model.user.{UserId, UserProfile}
import app.domain.user.User

object UserMapper {

  def toDomain(fromPersistent: UserPersistentModel): User = {
    val domain = new User(
        id = UserId(fromPersistent.email),
        profile = UserProfile(
          firstname = fromPersistent.firstname,
          surname = fromPersistent.surname,
          email = fromPersistent.email,
          datebirth = fromPersistent.datebirth
        ),
        registeredDateTime = fromPersistent.registeredDateTime,
        emailConfirmed = fromPersistent.isEmailConfirmed
    )

    fromPersistent.id match {
      case None => throw new IllegalArgumentException("A persisted UserPersistentModel is expected to have a surrogate id in order to be mapped to domain")
      case Some(value) if value.isInstanceOf[Long] => domain.setSurrogateId(fromPersistent.id)
    }

    domain
  }

  def toPersistent(user: User): UserPersistentModel= {
    new UserPersistentModel(
      id = user.getSurrogateId(),
      firstname = user.getProfile.firstname,
      surname = user.getProfile.surname,
      email = user.getProfile.email,
      datebirth = user.getProfile.datebirth,
      registeredDateTime = user.registeredDateTime,
      isEmailConfirmed = user.isEmailConfirmed()
    )
  }
}
