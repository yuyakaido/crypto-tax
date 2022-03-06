package binance

import common.Security
import okhttp3.Interceptor
import okhttp3.Response
import java.time.Instant

class BinanceHttpInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    companion object {
        private val PUBLIC_API_PATH_LIST = listOf(
            "/api/v3/exchangeInfo",
            "/api/v3/klines"
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.encodedPath
        if (PUBLIC_API_PATH_LIST.contains(path)) {
            return chain.proceed(chain.request())
        } else {
            val targetUrl = chain.request().url
                .newBuilder()
                .addEncodedQueryParameter(
                    "timestamp",
                    Instant.now().toEpochMilli().toString()
                )
                .build()
            val signature = Security.generateSignature(
                apiSecret = apiSecret,
                target = targetUrl.encodedQuery ?: ""
            )
            val newUrl = targetUrl
                .newBuilder()
                .addEncodedQueryParameter("signature", signature)
                .build()
            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .addHeader("X-MBX-APIKEY", apiKey)
                .build()
            return chain.proceed(newRequest)
        }
    }

}