package com.worldline.multiplatform.ui.executor

import kotlinx.coroutines.Dispatchers

class AndroidDispatcherProvider : CoroutineDispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
}