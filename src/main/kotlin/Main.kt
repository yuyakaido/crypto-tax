import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

@ExperimentalSerializationApi
fun main() {
    println("Started!")

    println("BYBIT_API_KEY = ${System.getProperty("BYBIT_API_KEY")}")
    println("BYBIT_API_SECRET = ${System.getProperty("BYBIT_API_SECRET")}")

//    val json = Json {
//        ignoreUnknownKeys = true
//    }
//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.bybit.com/")
//        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
//        .build()
//    val client = retrofit.create(HttpClient::class.java)
//    runBlocking {
//        val response = client.get()
//        println(response)
//    }

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