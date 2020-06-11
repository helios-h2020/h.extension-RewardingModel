package com.worldline.helios.rewardingstub.multiplatform.ui.executor

import kotlinx.coroutines.Dispatchers

class IOSDispatcherProvider : CoroutineDispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
}