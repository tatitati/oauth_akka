package app.infrastructure.persistence.third

import java.util.UUID
import app.domain.model.third.ThirdId
import app.domain.third.{Third, ThirdProfile}

object ThirdMapper {

  def toDomain(fromPersistent: ThirdPersistentModel): Third = {
    val domain = Third(
      thirdId = ThirdId(
        value = UUID.fromString(fromPersistent.id)
      ),
      profile = ThirdProfile(
        name = fromPersistent.name,
        callback = fromPersistent.callback,
        homepage = fromPersistent.homepage,
        description = fromPersistent.description
      )
    )

    domain.setSurrogateId(fromPersistent.surrogateId)

    domain
  }

  def toPersistent(third: Third): ThirdPersistentModel= {
    ThirdPersistentModel(
      surrogateId = third.getSurrogateId(),
      id = third.thirdId.toString,
      name = third.getProfile.name,
      callback = third.getProfile.callback,
      homepage = third.getProfile.homepage,
      description = third.getProfile.description
    )
  }
}