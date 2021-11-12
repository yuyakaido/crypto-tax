package bitbank

import common.Security
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Response
import java.time.Instant

class BitbankHttpInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url

        val nonce = Instant.now().toEpochMilli()
        val target = "$nonce${url.encodedPath}${url.encodedQuery?.let { "?$it" } ?: ""}"
        val signature = Security.generateSignature(
            apiSecret = apiSecret,
            target = target
        )

        val headers = mapOf(
            "ACCESS-KEY" to apiKey,
            "ACCESS-NONCE" to nonce.toString(),
            "ACCESS-SIGNATURE" to signature
        )

        val newRequest = originalRequest
            .newBuilder()
            .headers(headers.toHeaders())
            .build()

        return chain.proceed(newRequest)
    }

}