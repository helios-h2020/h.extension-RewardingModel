package com.worldline.helios.rewardingstub.multiplatform.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.worldline.helios.rewardingstub.multiplatform.utils.UnitTest
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.local.LocalDataSource
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote.RemoteDataSource
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Either
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import kotlin.test.Test

class CommonRepositoryTest : UnitTest() {

    private var remoteDataSource = mock<RemoteDataSource>()
    private var localDataSource = mock<LocalDataSource>()

    private lateinit var commonRepository: CommonRepository

    @Before
    fun setUp() {
        commonRepository = CommonRepository(remoteDataSource, localDataSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getForecast_should_return_forecast_when_no_errors() = runBlockingTest {
        val badajozCity = badajozCity()
        val badajozForecast = badajozForecast()
        whenever(remoteDataSource.getForecast(any())).thenReturn(Either.Right(badajozForecast))
        whenever(localDataSource.saveForecast(badajozForecast)).thenReturn(Either.Right(Success))

        val actualForecast = commonRepository.getForecast(badajozCity)

        assertRight(badajozForecast(), actualForecast)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getForecast_should_return_internet_error_when_no_internet() = runBlockingTest {
        val badajozCity = badajozCity()
        val noInternetError = Error.NoInternet
        whenever(remoteDataSource.getForecast(any())).thenReturn(Either.Left(noInternetError))

        val actualForecast = commonRepository.getForecast(badajozCity)

        assertLeft(noInternetError, actualForecast)
    }
}