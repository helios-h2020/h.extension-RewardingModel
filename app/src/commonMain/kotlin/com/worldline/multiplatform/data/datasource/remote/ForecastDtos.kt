package com.worldline.multiplatform.data.datasource.remote

import com.worldline.multiplatform.domain.model.*
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(
    val base: String,
    val clouds: CloudsDto,
    val cod: Int,
    val coord: CoordDto,
    val dt: Int,
    val id: Int,
    val main: MainDto,
    val name: String,
    val sys: SysDto,
    val visibility: Int,
    val weather: List<WeatherDto>,
    val wind: WindDto
) {
    fun toModel() = Forecast(
        base = base,
        clouds = clouds.toModel(),
        cod = cod,
        coord = coord.toModel(),
        dt = dt,
        id = id,
        main = main.toModel(),
        name = name,
        sys = sys.toModel(),
        visibility = visibility,
        weather = weather.map { it.toModel() },
        wind = wind.toModel()
    )
}

@Serializable
data class CloudsDto(
    val all: Int
) {
    fun toModel() = Clouds(all = all)
}

@Serializable
data class CoordDto(
    val lat: Double,
    val lon: Double
) {
    fun toModel() = Coord(lat = lat, lon = lon)
}

@Serializable
data class MainDto(
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
) {
    fun toModel() = Main(
        humidity = humidity,
        pressure = pressure,
        temperature = temp,
        temperatureMax = temp_max,
        temperatureMin = temp_min
    )
}

@Serializable
data class SysDto(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
) {
    fun toModel() = Sys(
        country = country,
        id = id,
        message = message,
        sunrise = sunrise,
        sunset = sunset,
        type = type
    )
}

@Serializable
data class WeatherDto(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) {
    fun toModel() = Weather(description = description, icon = icon, id = id, main = main)
}

@Serializable
data class WindDto(
    val deg: Int,
    val speed: Double
) {
    fun toModel() = Wind(directionInDegrees = deg, speed = speed)
}
