package br.com.piscioneri.covid19.data

import com.google.gson.annotations.SerializedName

data class ReportCountry(
    val cases: Int,
    val confirmed: Int,
    val country: String,
    val deaths: Int,
    val recovered: Int,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val datetime: String?
)