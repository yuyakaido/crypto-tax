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

@ExperimentalSerializationApi
fun main() {
    println("Started!")

    val json = Json {
        ignoreUnknownKeys = true
    }
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.bybit.com/")
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .build()
    val client = retrofit.create(HttpClient::class.java)
    runBlocking {
        val response = client.get()
        println(response)
    }

    println("Completed!")
}

interface HttpClient {
    @GET("/v2/public/funding/prev-funding-rate")
    suspend fun get(
        @Query("symbol") symbol: String = "BTCUSD"
    ): Response
}

@Serializable
data class Response(
    @SerialName("result") val result: Result
) {
    @Serializable
    data class Result(
        @SerialName("symbol") val symbol: String,
        @SerialName("funding_rate") val fundingRate: String,
        @SerialName("funding_rate_timestamp") val fundingRateTimestamp: Long
    )
}