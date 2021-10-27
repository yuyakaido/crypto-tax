import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.system.exitProcess

@ExperimentalSerializationApi
fun main() {
    println("Started!")

    val bybitApiKey = System.getProperty("BYBIT_API_KEY")
    val bybitApiSecret = System.getProperty("BYBIT_API_SECRET")
    println("BYBIT_API_KEY = $bybitApiKey")
    println("BYBIT_API_SECRET = $bybitApiSecret")

    val json = Json {
        ignoreUnknownKeys = true
    }
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.bybit.com/")
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .build()
    val client = retrofit.create(HttpClient::class.java)

    runBlocking {
        val response = client.getApiKeyInfo(
            queries = generateQueries(bybitApiKey, bybitApiSecret)
        )
        println(response)
    }

    println("Completed!")

    exitProcess(0)
}

private fun generateQueries(
    apiKey: String,
    apiSecret: String,
    parameters: Map<String, String> = emptyMap()
): Map<String, String> {
    val queryMap = parameters.plus(
        mapOf(
            "api_key" to apiKey,
            "timestamp" to System.currentTimeMillis().toString()
        )
    )
    val queryString = buildString {
        val iterator = queryMap.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            append("${entry.key}=${entry.value}")
            if (iterator.hasNext()) {
                append("&")
            }
        }
    }

    val algorithm = "HmacSHA256"
    val mac = Mac.getInstance(algorithm)
    mac.init(SecretKeySpec(apiSecret.toByteArray(), algorithm))
    val sign = mac.doFinal(queryString.toByteArray()).joinToString("") { String.format("%02x", it) }

    return queryMap.plus(
        "sign" to sign
    )
}

interface HttpClient {
    @GET("/v2/public/funding/prev-funding-rate")
    suspend fun getPreviousFundingRate(
        @Query("symbol") symbol: String = "BTCUSD"
    ): PreviousFundingRateResponse

    @GET("/v2/private/account/api-key")
    suspend fun getApiKeyInfo(
        @QueryMap queries: Map<String, String>
    ): ApiKeyInfoResponse
}

@Serializable
data class PreviousFundingRateResponse(
    @SerialName("result") val result: Result
) {
    @Serializable
    data class Result(
        @SerialName("symbol") val symbol: String,
        @SerialName("funding_rate") val fundingRate: String,
        @SerialName("funding_rate_timestamp") val fundingRateTimestamp: Long
    )
}

@Serializable
data class ApiKeyInfoResponse(
    @SerialName("result") val result: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("api_key") val apiKey: String
    )
}