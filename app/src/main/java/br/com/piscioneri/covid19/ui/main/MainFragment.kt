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
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        val list = arrayOf("Mundo", "Brasil", "Acre", "São Paulo", "Rio de Janeiro")
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
            list
        )
        dropdown.setAdapter(adapter)
        dropdown.setText(list[0], false)
        dropdown.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val selected = parent.adapter.getItem(position)
            Toast.makeText(context, selected.toString(), Toast.LENGTH_LONG).show();
        }
    }

}