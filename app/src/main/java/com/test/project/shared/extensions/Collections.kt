package com.test.project.shared.extensions

import androidx.compose.runtime.Immutable

@Immutable
data class ImmutableList<T>(val items: List<T> = emptyList())