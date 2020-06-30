package com.wordline.helios.rewarding.sdk.data.datasource.remote

import com.wordline.helios.rewarding.sdk.data.datasource.local.LocalDataSource
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.util.AttributeKey

class TokenFeature private constructor(
    private val tokenHeaderName: String,
    private val tokenProvider: LocalDataSource
) {

    class Config {
        var tokenHeaderName: String? = null
        var tokenProvider: LocalDataSource? = null
        fun build() = TokenFeature(
            tokenHeaderName ?: throw IllegalArgumentException("HeaderName should be contain"),
            tokenProvider ?: throw IllegalArgumentException("TokenProvider should be contain")
        )
    }

    companion object Feature : HttpClientFeature<Config, TokenFeature> {
        override val key = AttributeKey<TokenFeature>("TokenFeature")

        override fun prepare(block: Config.() -> Unit) = Config().apply(block).build()

        override fun install(feature: TokenFeature, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                feature.tokenProvider.getToken().apply {
                    this.fold(
                        error = {},
                        success = { context.header(feature.tokenHeaderName, "Bearer $it") }
                    )
                }
            }
        }
    }
}