package builders

import app.domain.thirdstore.{Code, ResourceCode}
import builders.authorizes.BuildScope

object BuildResourceCode {

  def any(): ResourceCode  = {
    new ResourceCode(
      thirdProfile = BuildThirdProfile.any(),
      BuildOwnerProfile.any(),
      BuildScope.any(),
      BuildCode.anyLive()
    )
  }
}

