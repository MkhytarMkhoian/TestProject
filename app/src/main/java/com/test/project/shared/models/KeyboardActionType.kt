package com.test.project.shared.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class KeyboardActionType : Parcelable {

    @Parcelize
    object Done : KeyboardActionType()

    @Parcelize
    object Next : KeyboardActionType()
}