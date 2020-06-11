package com.worldline.helios.rewardingstub.multiplatform.ui.presenter

import com.worldline.helios.rewardingstub.multiplatform.data.repository.Repository
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Forecast
import com.worldline.helios.rewardingstub.multiplatform.ui.error.ErrorHandler
import com.worldline.helios.rewardingstub.multiplatform.ui.executor.Executor
import kotlinx.coroutines.launch

class HomePresenter(
    private val repository: Repository,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: HomeView
) : Presenter<HomeView>(errorHandler, executor, view) {

    override fun attach() {
        getForecast("Badajoz")
    }

    private fun getForecast(city: String) {
        scope.launch {
            execute { repository.getForecast(city) }.fold(
                error = { onRetry(it) { getForecast(city) } },
                success = { view.showForecast(it) }
            )
        }
    }
}

interface HomeView : View {
    fun showForecast(forecast: Forecast)
}
