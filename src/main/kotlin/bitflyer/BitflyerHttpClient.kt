package bitflyer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonArray
import retrofit2.Response
import retrofit2.http.GET

@ExperimentalSerializationApi
interface BitflyerHttpClient {

    @GET("markets")
    suspend fun getMarkets(): Response<JsonArray>

}