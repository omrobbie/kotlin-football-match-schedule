package com.omrobbie.footballmatchschedule.utils

import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { UI }
}

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}
