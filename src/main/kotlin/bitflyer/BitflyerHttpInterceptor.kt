package bitflyer

import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Response
import java.time.Instant

class BitflyerHttpInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val timestamp = Instant.now().epochSecond
        val method = request.method
        val path = request.url.encodedPath
        val target = "$timestamp$method$path"
        val headers = mapOf(
            "ACCESS-KEY" to apiKey,
            "ACCESS-TIMESTAMP" to timestamp.toString(),
            "ACCESS-SIGN" to BitflyerSigner.generateSignature(
                apiKey = apiKey,
                apiSecret = apiSecret,
                target = target
            )
        )
        val newRequest = chain.request()
            .newBuilder()
            .headers(headers.toHeaders())
            .build()
        return chain.proceed(newRequest)
    }

}