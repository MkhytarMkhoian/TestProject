package com.test.project.shared.coroutines

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchers {
    val ioContext: CoroutineContext
    val uiContext: CoroutineContext
    val defaultContext: CoroutineContext
}

@Singleton
class CoroutineDispatchersImpl @Inject constructor() : CoroutineDispatchers {
    override val ioContext: CoroutineContext
        get() = Dispatchers.IO
    override val uiContext: CoroutineContext
        get() = Dispatchers.Main
    override val defaultContext: CoroutineContext
        get() = Dispatchers.Default
}

