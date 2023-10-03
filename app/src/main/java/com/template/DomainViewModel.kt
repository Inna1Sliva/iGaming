package com.template

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.model.DomainState
import com.template.model.Model
import kotlinx.coroutines.launch

class DomainViewModel(private var repository: DomainRepository):ViewModel() {

    val state: LiveData<DomainState> = repository.getObserverDomainState()
    val model:LiveData<Model> = repository.getObserverModelState()

        init {
    viewModelScope.launch {
        repository.getDomain()
             }
            viewModelScope.launch {
                repository.getDataSharedPref()
            }
        }
    fun processIntent(state: DomainIntent) {
          when(state){
              is DomainIntent.Data-> loadData()
              is DomainIntent.Seve-> seveData(DomainState("${state.domain}"))
          }
      }

    private fun loadData():LiveData<Model> {
        return model
    }

    private fun seveData(domain: DomainState) {
             repository.insert(domain)
    }


}