package test.app.domain.model.site

import app.domain.model.site.SiteCredentials
import test.builders.Faker

object BuildSiteCredentials {

  def any(
           withClientid: String = Faker.text(),
           withClientsecret: String = Faker.text()
         ): SiteCredentials = {

    SiteCredentials(withClientid, withClientsecret)
  }

  def specific(): SiteCredentials = {
    SiteCredentials(
      clientId = "client_id",
      clientSecret = "client_secret"
    )
  }
}
