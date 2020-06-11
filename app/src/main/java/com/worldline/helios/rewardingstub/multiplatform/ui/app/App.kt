package com.worldline.helios.rewardingstub.multiplatform.ui.app

import android.app.Application
import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.local.CommonLocalDataSource
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.local.LocalDataSource
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote.CommonRemoteDataSource
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote.RemoteDataSource
import com.worldline.helios.rewardingstub.multiplatform.data.repository.CommonRepository
import com.worldline.helios.rewardingstub.multiplatform.data.repository.Repository
import com.worldline.helios.rewardingstub.multiplatform.ui.error.ErrorHandler
import com.worldline.helios.rewardingstub.multiplatform.ui.executor.Executor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(appModule(this@App))
        import(domainModule)
        import(dataModule)
    }

}

fun appModule(context: Context) = Kodein.Module("appModule") {
    bind<Context>() with singleton { context }
    bind<Executor>() with singleton { Executor() }
    bind<ErrorHandler>() with singleton { ErrorHandler(context) }
}

val domainModule = Kodein.Module("domainModule") {
    bind<Repository>() with singleton { CommonRepository(remote = instance(), local = instance()) }
}

val dataModule = Kodein.Module("dataModule") {
    bind<RemoteDataSource>() with singleton { CommonRemoteDataSource() }
    bind<LocalDataSource>() with singleton {
        val context: Context = instance()
        CommonLocalDataSource(AndroidSettings(context.getSharedPreferences("bd", Context.MODE_PRIVATE)))
    }
}

const val ACTIVITY_MODULE = "activityModule"
