package com.worldline.helios.rewardingstub.multiplatform.ui.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class Executor(
    coroutineDispatcherProvider: CoroutineDispatcherProvider = IOSDispatcherProvider()
) {
    actual val main: CoroutineDispatcher = coroutineDispatcherProvider.main
    actual val bg: CoroutineDispatcher = coroutineDispatcherProvider.default
}

object Main : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        NSRunLoop.mainRunLoop().performBlock {
            block.run()
        }
    }
}