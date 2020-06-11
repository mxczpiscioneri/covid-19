package br.com.piscioneri.covid19.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "https://covid19-brazil-api.now.sh/api/report/v1/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY }
        ).build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}