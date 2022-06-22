package com.test.project.shared.models

data class InputItem(
    val id: String,
    val placeholder: String,
    val subLabel: String,
    val isError: Boolean,
    val value: String,
    val maxLength: Int?,
    val isRequired: Boolean,
    val keyboardActionType: KeyboardActionType
) : FeedItem {

    override fun id(): Long = id.hashCode().toLong()
}
