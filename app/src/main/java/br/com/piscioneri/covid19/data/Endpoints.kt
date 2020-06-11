package br.com.piscioneri.covid19.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {
    @GET("brazil")
    fun getReportBrazil(): Call<Result>

    @GET("brazil/uf/{uf}")
    fun getReportState(@Path("uf") uf: String): Call<ReportState>
}
