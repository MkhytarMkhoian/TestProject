package com.test.project.shared.models

interface FeedItem {
    /**Warning: keep different ids for each type of item*/
    fun id(): Long
}