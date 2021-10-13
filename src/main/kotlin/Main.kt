import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET

@ExperimentalSerializationApi
fun main() {
    println("Started!")

    val json = Json {
        ignoreUnknownKeys = true
    }
    val retrofit = Retrofit.Builder()
        .baseUrl("https://httpbin.org/")
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
    @GET("get")
    suspend fun get(): Response
}

@Serializable
data class Response(
    @SerialName("origin") val origin: String,
    @SerialName("url") val url: String
)