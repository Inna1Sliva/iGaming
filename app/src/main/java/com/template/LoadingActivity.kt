package com.template


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityLoadingBinding
import com.template.model.DomainState
import okhttp3.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class LoadingActivity : AppCompatActivity() {

   // private var sharedPreferences :SharedPreferences? = null
   // private var result:Boolean = false
   // private var resultTrue:Boolean = true
   // private var url:String = "note domain"
   private val vm:DomainViewModel by viewModel<DomainViewModel>()

    private lateinit var binding: ActivityLoadingBinding
   // @SuppressLint("AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

       internetCheck()

        //Handler(Looper.getMainLooper()).postDelayed({
          //
          //sharedPreferences =  this.getSharedPreferences(APP_RESULT, MODE_PRIVATE)
           // 

       // }, 1000)

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
                    Log.d("Network", "wifi connected")

                    vm.state.observe(this) { state ->
                        Log.d("Network", "wifi connected")
                        if (state.domain?.isEmpty() == true){
                            vm.processIntent(DomainIntent.Seve(DomainState("${state.domain}")))
                            showError()
                        }else{

                             showDomain(state.domain)
                        }

                    }






                    true
                }
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                   // val appPrefBool = sharedPreferences?.getBoolean(APP_RESULT_BOOL, false)
                   // val appPrefResultURL = sharedPreferences?.getString(APP_RESULT_URL, null)
                   // if (appPrefBool!=null && appPrefResultURL !=null){
                    //    prefResult(appPrefBool,appPrefResultURL)
                   // }else{
                     //   remoteConfigRead()
                       // Log.d("Network","Server connection")
                    //}
                    //Log.d("Network", "cellular network connected")
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



    private fun showError() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun showDomain(domain: String?) {
        val wa = Intent(this, WebActivity::class.java)
        wa.putExtra("url", domain)
        startActivity(wa)
        finish()
    }

    private fun showLoading() {
        Toast.makeText(this, "brogressBar_Start",Toast.LENGTH_SHORT).show()

        binding.progresBar.visibility = View.VISIBLE
    }


    //private fun prefResult(appPrefBoolean:Boolean, appPrefResultURL:String) {
     //   if (!appPrefBoolean){
      //      startActivity(Intent(this, MainActivity::class.java))
      //  }else {
          //
  //  }

   // private fun remoteConfigRead() {
     //   remoteConfig?.fetchAndActivate()
        ///    ?.addOnCompleteListener(this) { task ->
            //    if (task.isSuccessful) {
              //      val domanFromFirebase = remoteConfig?.getString("check_link")
              ////      getLinkServer(domanFromFirebase!!)
               // } else  {
                   // resultServer()


                //}
            //}
    //}

   // @SuppressLint("SuspiciousIndentation")
    //private fun getLinkServer(domanFromFirebase: String) {
     //   if (domanFromFirebase.isNotEmpty()){
      //      callServerResult(domanFromFirebase)
       // }else{
          //  resultServer()
           // startActivity(Intent(this, MainActivity::class.java))
          //  finish()
       // }

    //}

   

  //  private fun resultServer() {
     //  val edit = sharedPreferences!!.edit()
      //  edit.putBoolean(APP_RESULT_BOOL, result)
      //  edit.putString(APP_RESULT_URL, url)
       // edit.apply()
       // startActivity(Intent(this, MainActivity::class.java))
       // finish()
    //}



}