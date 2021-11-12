package bittrex

import common.Security
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Response
import java.time.Instant

class BittrexHttpInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val body = originalRequest.body?.toString() ?: ""

        val timestamp = Instant.now().toEpochMilli()
        val contentHash = Security.generateHash(body)
        val target = "$timestamp${originalRequest.url}${originalRequest.method}$contentHash"
        val signature = Security.generateSignature(
            apiSecret = apiSecret,
            target = target,
            algorithm = Security.SignatureAlgorithm.HMAC_SHA_512
        )

        val headers = mapOf(
            "Api-Key" to apiKey,
            "Api-Timestamp" to timestamp.toString(),
            "Api-Content-Hash" to contentHash,
            "Api-Signature" to signature
        )

        val newRequest = originalRequest
            .newBuilder()
            .headers(headers.toHeaders())
            .build()

        return chain.proceed(newRequest)
    }

}