package builders

import app.domain.thirdstore.{Third, ThirdProfile}

object BuildThird {
    def any(
           withThirdProfile: ThirdProfile = BuildThirdProfile.any()
        ): Third = {

      new Third(withThirdProfile)

    }
}
