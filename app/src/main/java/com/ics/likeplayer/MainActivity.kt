package com.ics.likeplayer

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ics.likeplayer.FurtherActivity.Searchwhatactivity

class MainActivity : AppCompatActivity() {
    private var cResolver: ContentResolver? = null
    private lateinit var toolbar: Toolbar
    private lateinit var searchonmp3: LinearLayout

    private var brightness = 150
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        toolbar = findViewById(R.id.toolbar);
        searchonmp3 = findViewById(R.id.searchonmp3);
        //Get the content resolver
        cResolver = contentResolver
        searchonmp3.setOnClickListener {
            intent = Intent(applicationContext, Searchwhatactivity::class.java)
            startActivity(intent)
        }
        if (checkSystemWritePermission()) {
            try { // To handle the auto
                Settings.System.putInt(
                    cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                )
                //Get the current system brightness
                brightness = Settings.System.getInt(
                    cResolver,
                    Settings.System.SCREEN_BRIGHTNESS, brightness
                )
            } catch (e: SettingNotFoundException) { //Throw an error case it couldn't be retrieved
                Log.e("Error", "Cannot access system brightness")
                e.printStackTrace()
            }

        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        this.supportActionBar = findViewById<Toolbar>(R.id.appabr);
        (this as AppCompatActivity).setSupportActionBar(toolbar)
//        (this as AppCompatActivity).supportActionBar?.title = ;
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigat_song, R.id.navigation_palylist
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    private fun checkSystemWritePermission(): Boolean {
        var retVal = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this)
            Log.d("TAG", "Can Write Settings: $retVal")
            if (retVal) {
                Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show()
                openAndroidPermissionsMenu()
            }
        }
        return retVal;
    }

    private fun openAndroidPermissionsMenu() {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:" + this.packageName)
        startActivity(intent)
    }
}
