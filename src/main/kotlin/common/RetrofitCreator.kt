package common

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.serializersModuleOf
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@ExperimentalSerializationApi
object RetrofitCreator {

    private val json = Json {
        ignoreUnknownKeys = true
        serializersModule = serializersModuleOf(BigDecimalSerializer)
            .plus(serializersModuleOf(SymbolSerializer))
            .plus(serializersModuleOf(AssetSerializer))
            .plus(serializersModuleOf(ZonedDateTimeSerializer))
            .plus(serializersModuleOf(LocalDateSerializer))
    }
    private val loggingInterceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.NONE }

    fun newInstance(
        baseUrl: String,
        interceptors: List<Interceptor> = emptyList()
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .apply { interceptors.forEach { interceptor -> addInterceptor(interceptor) } }
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    fun getJson(): Json {
        return json
    }

}