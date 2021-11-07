package poloniex

import common.Signer
import okhttp3.FormBody
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Response

class PoloniexHttpInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val body = chain.request().body as? FormBody
        return body?.let {
            val builder = StringBuilder()
            (0 until body.size).forEach { index ->
                builder.append(body.encodedName(index))
                builder.append("=")
                builder.append(body.encodedValue(index))
                if (index < body.size - 1) {
                    builder.append("&")
                }
            }
            val headers = mapOf(
                "Key" to apiKey,
                "Sign" to Signer.generateSignature(
                    apiKey = apiKey,
                    apiSecret = apiSecret,
                    target = builder.toString(),
                    algorithm = Signer.Algorithm.HMAC_SHA_512
                )
            )
            val newRequest = chain.request()
                .newBuilder()
                .headers(headers.toHeaders())
                .build()
            chain.proceed(newRequest)
        } ?: chain.proceed(chain.request())
    }

}