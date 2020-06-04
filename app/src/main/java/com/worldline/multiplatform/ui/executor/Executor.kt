package com.worldline.multiplatform.ui.executor

import kotlinx.coroutines.CoroutineDispatcher

actual class Executor(
    coroutineDispatcherProvider: CoroutineDispatcherProvider = AndroidDispatcherProvider()
) {
    actual val main: CoroutineDispatcher = coroutineDispatcherProvider.main
    actual val bg: CoroutineDispatcher = coroutineDispatcherProvider.io
}
