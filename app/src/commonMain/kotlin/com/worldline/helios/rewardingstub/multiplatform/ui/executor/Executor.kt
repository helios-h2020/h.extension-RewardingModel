package com.worldline.helios.rewardingstub.multiplatform.ui.executor

import kotlinx.coroutines.CoroutineDispatcher

expect class Executor {
    val main: CoroutineDispatcher
    val bg: CoroutineDispatcher
}
