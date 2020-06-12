package br.com.piscioneri.covid19

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.piscioneri.covid19.ui.main.MainFragment
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        MobileAds.initialize(this) {}
    }
}