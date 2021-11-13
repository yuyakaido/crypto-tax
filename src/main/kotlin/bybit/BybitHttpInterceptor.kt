package bybit

import common.Security
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Interceptor
import okhttp3.Response
import java.time.Instant

@ExperimentalSerializationApi
class BybitHttpInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val originalParameters = (0 until originalUrl.querySize).associate { index ->
            originalUrl.queryParameterName(index) to originalUrl.queryParameterValue(index)
        }

        // The parameters must be ordered in alphabetical order.
        // https://bybit-exchange.github.io/docs/inverse/#t-authentication
        val signedParameters = originalParameters.plus(
             listOf(
                "api_key" to apiKey,
                "timestamp" to Instant.now().toEpochMilli().toString()
            )
        ).toSortedMap()
        val target = buildString {
            val iterator = signedParameters.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                append("${entry.key}=${entry.value}")
                if (iterator.hasNext()) {
                    append("&")
                }
            }
        }

        val newParameters = signedParameters.plus(
            "sign" to Security.generateSignature(apiSecret, target)
        )
        val newUrl = originalUrl.newBuilder()
            .apply {
                newParameters.forEach { entry ->
                    removeAllEncodedQueryParameters(entry.key)
                    addEncodedQueryParameter(entry.key, entry.value)
                }
            }
            .build()
        val newRequest = originalRequest
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

}