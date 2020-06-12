package br.com.piscioneri.covid19.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.piscioneri.covid19.R
import br.com.piscioneri.covid19.data.*
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_list_item_1,
            Places.toListDescription()
        )
        dropdown.setAdapter(adapter)
        dropdown.setText("Brasil", false)
        dropdown.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val selected = parent.adapter.getItem(position).toString()
            getReport(Places.getCode(selected))
        }

        getReport(null)

        swipe_refresh_layout.setColorSchemeColors(
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.colorPrimaryDark)
        );
        swipe_refresh_layout.setOnRefreshListener {
            dropdown.setText("Brasil", false)
            getReport(null)
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun getReport(place: String?) {
        progress_bar.visibility = View.VISIBLE
        card_report.visibility = View.INVISIBLE

        val request = ServiceBuilder.buildService(Endpoints::class.java)
        if (place == null || place == "br") {
            val call = request.getReportBrazil()
            call.enqueue(object : Callback<Result> {
                override fun onResponse(call: Call<Result>, response: Response<Result>) {
                    if (response.isSuccessful) {
                        showData(
                            response.body()?.data?.confirmed.toString(),
                            response.body()?.data?.deaths.toString(),
                            response.body()?.data?.recovered.toString(),
                            response.body()?.data?.updatedAt!!
                        )
                    } else {
                        Toast.makeText(
                            context,
                            response.errorBody().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Result>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            val call = request.getReportState(place)
            call.enqueue(object : Callback<ReportState> {
                override fun onResponse(call: Call<ReportState>, response: Response<ReportState>) {
                    if (response.isSuccessful) {
                        showData(
                            response.body()?.cases.toString(),
                            response.body()?.deaths.toString(),
                            response.body()?.refuses.toString(),
                            response.body()?.datetime!!
                        )
                    } else {
                        Toast.makeText(
                            context,
                            response.errorBody().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ReportState>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun showData(confirmed: String, deaths: String, recovered: String, subtitle: String) {
        progress_bar.visibility = View.GONE
        card_report.visibility = View.VISIBLE
        swipe_refresh_layout.isRefreshing = false
        txt_confirmed.text = confirmed
        txt_deaths.text = deaths
        txt_recovered.text = recovered
        txt_subtitle.text = "Última atualização em ${parseDate(subtitle)}"
    }

    fun parseDate(inputDateString: String): String? {
        var outputDateString: String? = null
        try {
            val date = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            ).parse(inputDateString)
            val simpleData = SimpleDateFormat("dd/MM/yyy HH:mm:ss")
            simpleData.timeZone = TimeZone.getTimeZone("GMT-9")
            outputDateString = simpleData.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateString
    }

}