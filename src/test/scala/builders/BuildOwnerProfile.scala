package builders

import app.domain.ownerstore.OwnerProfile
import com.github.nscala_time.time.Imports.{DateTime, _}

object BuildOwnerProfile {

  def any(
           withFirstname: String = Faker.text(),
           withSurname: String = Faker.text(),
           withEmail: String = Faker.text(),
           withEmailConfirmed: Boolean = Faker.boolean(),
           withDatebirth: DateTime = Faker.date()
         ): OwnerProfile = {

    new OwnerProfile(
      firstname = withFirstname,
      surname = withSurname,
      email = withEmail,
      emailconfirmed = withEmailConfirmed,
      datebirth = withDatebirth
    )
  }
}
