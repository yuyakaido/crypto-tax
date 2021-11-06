package common

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import okhttp3.MediaType
import retrofit2.Retrofit

@ExperimentalSerializationApi
object RetrofitCreator {

    private val json = Json {
        ignoreUnknownKeys = true
        serializersModule = serializersModuleOf(BigDecimalSerializer)
    }

    fun newInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

}