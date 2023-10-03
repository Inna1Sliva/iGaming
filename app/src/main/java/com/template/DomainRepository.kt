package com.template

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.template.model.DomainState
import com.template.model.Model
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DomainRepository (private var remoteConfig:FirebaseRemoteConfig, private var editor: Editor,  private var sharedPreferences: SharedPreferences){

    private var state = MutableLiveData<DomainState>()
    private var model = MutableLiveData<Model>()

    init {
        state = MutableLiveData()
        model= MutableLiveData()
    }

    fun insert(domain:DomainState){
        editor.putString(Constant.APP_RESULT_URL, domain.domain)
        editor.apply()

    }
    suspend fun getDataSharedPref()= coroutineScope {
        launch {
            val url = sharedPreferences.getString(Constant.APP_RESULT_URL, null)
            val modelState = Model(url)
            model.value = modelState
        }
    }
    fun getObserverModelState():MutableLiveData<Model>{
        return model
    }

    fun getObserverDomainState():MutableLiveData<DomainState>{
        return state
    }
    suspend fun getDomain()= coroutineScope {
        launch {
            remoteConfig.fetchAndActivate().addOnCompleteListener{task->
                            if (task.isSuccessful){
                                val domainConfig = remoteConfig?.getString("check_link")
                                Log.d("FIREBAS", "${domainConfig}")

                                val domainState = DomainState(domainConfig)
                                state.value = domainState
                            }else{
                                val domainStateNull = DomainState(null)
                                state.value =domainStateNull
                            }

                        }
                    }
                }

}
