package app.infrastructure.Persistence.resource

import java.util.UUID

import app.domain.auth.{Code, Scope}
import app.domain.owner.OwnerProfile
import app.domain.resource.ResourceByCode
import app.domain.third.{Third, ThirdCredentials, ThirdProfile}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{Json, Reads}

object ResourceByCodeSerializer {

  def toJson(resourceByCode: ResourceByCode): String = {

    val memento = resourceByCode.memento()
    val givenMap = Json.obj(
      "third" -> Json.obj(
          "surrogateid" -> memento.thirdSurrogateId,
          "uuid" -> memento.thirdUuid,
          "name" -> memento.thirdProfileName,
          "clientid" -> memento.thirdClientId,
          "clientsecret" -> memento.thirdClientSecret,
          "callback" -> memento.thirdCallback,
          "homepage" -> memento.thirdHomepage,
          "description" -> memento.thirdDescription
        ),
        "ownerProfile" -> Json.obj(
          "firstname" -> memento.ownerFirstname,
          "surname" -> memento.ownerSurname,
          "email" -> memento.ownerEmail,
          "emailconfirmed" -> memento.ownerEmailconfirmed,
          "datebirth" -> memento.ownerDatebirth.toString()
        ),
        "scope" -> Json.obj(
          "firstname" -> memento.scopeFirstname,
          "surname" -> memento.scopeSurname,
          "email" -> memento.scopeEmail
        ),
        "code" -> Json.obj(
          "code" -> memento.code,
          "expiresIn" -> memento.codeExpiresin,
          "generatedIn" -> memento.codeGeneratedIn.toString(),
          "state" -> memento.codeState
        )
    )

    Json.stringify(givenMap)
  }

  def toDomain(serialized: String): ResourceByCode = {

    val jodaDateReads = Reads[DateTime](js =>
      js.validate[String].map[DateTime](dtString =>
        DateTime.parse(dtString, DateTimeFormat.forPattern( "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        ))
      )
    )

    val parsed = Json.parse(serialized)

    val surrogateId = (parsed \ "third" \ "surrogateid").as[Long]
    val uuid = (parsed \ "third" \ "uuid").as[String]


    val thirdProfile = new ThirdProfile(
      name = (parsed \ "third" \ "name").as[String],
      callback = (parsed \ "third" \ "callback").as[String],
      homepage = (parsed \ "third" \ "homepage").as[String],
      description = (parsed \ "third" \ "description").as[String]
    )

    val thirdCredentials = ThirdCredentials(
      clientId = (parsed \ "third" \ "clientid").as[String],
      clientSecret = (parsed \ "third" \ "clientsecret").as[String],
    )

    val ownerProfile = new OwnerProfile(
      firstname = (parsed \ "ownerProfile" \ "firstname").as[String],
      surname = (parsed \ "ownerProfile" \ "surname").as[String],
      email = (parsed \ "ownerProfile" \ "email").as[String],
      emailconfirmed = (parsed \ "ownerProfile" \ "emailconfirmed").as[Boolean],
      datebirth = (parsed \ "ownerProfile" \ "datebirth").as[DateTime](jodaDateReads),
    )

    val scope = Scope(
      firstname = (parsed \ "scope" \ "firstname").as[Boolean],
      surname = (parsed \ "scope" \ "surname").as[Boolean],
      email = (parsed \ "scope" \ "email").as[Boolean],
    )

    val code = Code(
      code = (parsed \ "code" \ "code").as[String],
      expiresIn = (parsed \ "code" \ "expiresIn").as[Int],
      generatedIn = (parsed \ "code" \ "generatedIn").as[DateTime](jodaDateReads),
      state = (parsed \ "code" \ "state").as[String],
    )

    val third = Third(
      Uuid = UUID.fromString(uuid),
      profile = thirdProfile,
      credentials = thirdCredentials
    )
    third.setSurrogateId(Some(surrogateId))

    new ResourceByCode(
      third,
      ownerProfile = ownerProfile,
      scope = scope,
      code = code
    )
  }
}
