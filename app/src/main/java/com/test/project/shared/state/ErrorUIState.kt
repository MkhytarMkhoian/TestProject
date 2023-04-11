package com.test.project.shared.state

data class ErrorUIState(
    val showNetworkErrorMessage: Boolean = false,
    val showUnknownErrorMessage: Boolean = false,
){
    fun hasError(): Boolean = showNetworkErrorMessage || showUnknownErrorMessage
}