package com.test.project.features.player.models

import android.os.Parcelable
import com.test.project.features.links.models.StationModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerBundle(
  val model: StationModel,
) : Parcelable