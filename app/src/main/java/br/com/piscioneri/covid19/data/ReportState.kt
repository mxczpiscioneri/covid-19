package br.com.piscioneri.covid19.data

data class ReportState(
    val cases: Int,
    val datetime: String,
    val deaths: Int,
    val refuses: Int,
    val state: String,
    val suspects: Int,
    val uf: String,
    val uid: Int
)