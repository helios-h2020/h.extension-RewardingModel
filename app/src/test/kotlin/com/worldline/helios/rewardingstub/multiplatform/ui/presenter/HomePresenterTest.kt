package com.worldline.helios.rewardingstub.multiplatform.ui.presenter

import com.nhaarman.mockitokotlin2.*
import com.worldline.helios.rewardingstub.multiplatform.data.repository.Repository
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Either
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error
import com.worldline.helios.rewardingstub.multiplatform.utils.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class HomePresenterTest : UnitTest() {

    private var repository = mock<Repository>()
    private var view = mock<HomeView>()

    private lateinit var homePresenter: HomePresenter

    @Before
    fun setUp() {
        homePresenter =
            HomePresenter(repository, errorHandler(), testExecutor(), view)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun show_forecast_success() = runBlockingTest {
        val badajozCity = badajozCity()
        val badajozForecast = badajozForecast()
        whenever(repository.getForecast(badajozCity)).thenReturn(Either.Right(badajozForecast))

        homePresenter.attach()

        verify(view).showForecast(badajozForecast)
        verifyNoMoreInteractions(view)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun show_retry_when_no_internet() = runBlockingTest {
        val badajozCity = badajozCity()
        val noInternetError = Error.NoInternet
        whenever(repository.getForecast(badajozCity)).thenReturn(Either.Left(Error.NoInternet))
        whenever(context.getString(any())).thenReturn("no internet")

        homePresenter.attach()

        verify(view).showRetry(eq("no internet"), any())
        verifyNoMoreInteractions(view)
    }
}