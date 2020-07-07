package com.example.calesports

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.calesports.database.CryptonicDatabase
import com.example.calesports.helpers.InflateDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val manager = NetManager(this.applicationContext)
        val errorView = findViewById<ImageView>(R.id.errorImage)

        if (!manager.isConnectedToInternet!!) {
            errorView.visibility = View.VISIBLE
        } else {
            errorView.visibility = View.GONE

            val application = requireNotNull(this).application
            val dataSource = CryptonicDatabase.getInstance(application).cryptonicDatabaseDao
            InflateDatabase(dataSource).inflateDb()

            val navController = findNavController(R.id.nav_host_fragment)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_csgo, R.id.navigation_lol
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            navView.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{ menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_csgo -> {
                        navController.navigate(R.id.action_global_csgoFragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_lol -> {
                        navController.navigate(R.id.action_global_lolFragment)
                        return@OnNavigationItemSelectedListener true
                    }
/*                    R.id.navigation_dota -> {
                        navController.navigate(R.id.action_global_dotaFragment)
                        return@OnNavigationItemSelectedListener true
                    }*/
                }
                false
            }
        }
    }
}

class NetManager constructor(var applicationContext: Context) {
    val isConnectedToInternet: Boolean?
        get() = with(
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
        ) {
            isConnectedToInternet()
        }

    fun ConnectivityManager.isConnectedToInternet() = isConnected(getNetworkCapabilities(activeNetwork))

    fun isConnected(networkCapabilities: NetworkCapabilities?): Boolean {
        return when (networkCapabilities) {
            null -> false
            else -> with(networkCapabilities) {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
        }
    }
}
