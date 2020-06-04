package com.worldline.multiplatform.ui.presenter

import com.worldline.multiplatform.data.repository.Repository
import com.worldline.multiplatform.domain.model.Forecast
import com.worldline.multiplatform.ui.error.ErrorHandler
import com.worldline.multiplatform.ui.executor.Executor
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
