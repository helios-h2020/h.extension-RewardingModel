package com.worldline.helios.sampleapp.ui.app

import android.app.Application
import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.wordline.helios.rewarding.sdk.RewardingSdk
import com.wordline.helios.rewarding.sdk.RewardingSdkImpl
import com.wordline.helios.rewarding.sdk.data.datasource.local.CommonLocalDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.local.LocalDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.remote.CommonRemoteDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RemoteDataSource
import com.wordline.helios.rewarding.sdk.data.repository.CommonRepository
import com.wordline.helios.rewarding.sdk.data.repository.Repository
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor
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

    override fun onCreate() {
        super.onCreate()
        RewardingSdkImpl.init(this)
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
    bind<RewardingSdk>() with singleton { RewardingSdkImpl() }
    bind<RemoteDataSource>() with singleton { CommonRemoteDataSource(localDataSource = instance()) }
    bind<LocalDataSource>() with singleton {
        val context: Context = instance()
        CommonLocalDataSource(
            AndroidSettings(
                context.getSharedPreferences(
                    "bd",
                    Context.MODE_PRIVATE
                )
            )
        )
    }
}

const val ACTIVITY_MODULE = "activityModule"
