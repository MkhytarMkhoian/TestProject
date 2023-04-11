package com.test.project.shared.interactors

import com.test.project.R
import com.test.project.shared.resources.ResStorage
import javax.inject.Inject

class ErrorMessageInteractor @Inject constructor(
  private val resStorage: ResStorage,
) : Interactor {

  fun getNetworkProblemError(): String {
    return resStorage.getString(R.string.all_no_internet_connection)
      .plus(" ")
      .plus(resStorage.getString(R.string.all_connect_to_wi_fi_network_or_enable_mobile_data_and_try_again))
  }

  fun getUnknownProblemError(): String {
    return resStorage.getString(R.string.all_whoops)
      .plus(" ")
      .plus(resStorage.getString(R.string.all_sorry_something_went_wrong_give_it_another_try))
  }
}

