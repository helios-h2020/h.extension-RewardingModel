package com.worldline.multiplatform.utils

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import com.worldline.multiplatform.domain.model.*
import com.worldline.multiplatform.ui.error.ErrorHandler
import com.worldline.multiplatform.ui.executor.Executor
import kotlin.test.assertTrue
import kotlin.test.asserter

open class UnitTest {

    protected var context = mock<Context>()

    protected fun errorHandler() = ErrorHandler(context)

    protected fun testExecutor() = Executor(coroutineDispatcherProvider = TestDispatcherProvider())

    fun <L, R, T> assertRight(expected: T, actual: Either<L, R>, message: String? = null) {
        assertTrue { actual.isRight }
        if (actual is Either.Right) {
            asserter.assertEquals(message, expected, actual.success)
        }
    }

    fun <L, R, T> assertLeft(expected: T, actual: Either<L, R>, message: String? = null) {
        assertTrue { actual.isLeft }
        if (actual is Either.Left) {
            asserter.assertEquals(message, expected, actual.error)
        }
    }

    fun badajozCity() = "Badajoz"

    fun badajozForecast() = Forecast(
        base = "stations",
        clouds = Clouds(all = 90),
        cod = 200,
        coord = Coord(lat = 51.51, lon = 0.13),
        dt = 1485789600,
        id = 2643743,
        main = Main(
            humidity = 81,
            pressure = 1012,
            temperature = 280.32,
            temperatureMax = 281.15,
            temperatureMin = 279.15
        ), name = "Badajoz",
        sys = Sys(
            country = "GB",
            id = 5091,
            message = 0.0103,
            sunrise = 1485762037,
            sunset = 1485794875,
            type = 1
        ),
        visibility = 10000,
        weather = listOf(
            Weather(
                description = "light intensity drizzle",
                icon = "09d",
                id = 300,
                main = "Drizzle"
            )
        ),
        wind = Wind(directionInDegrees = 80, speed = 4.1)
    )
}