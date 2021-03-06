package com.wordline.helios.rewarding.sdk.executor

import kotlinx.coroutines.CoroutineDispatcher

class Executor(
    coroutineDispatcherProvider: CoroutineDispatcherProvider = AndroidDispatcherProvider()
) {
    val main: CoroutineDispatcher = coroutineDispatcherProvider.main
    val bg: CoroutineDispatcher = coroutineDispatcherProvider.io
}
