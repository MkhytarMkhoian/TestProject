package com.test.project.shared.extensions

import android.content.Intent

fun Intent.external(): Intent = addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
