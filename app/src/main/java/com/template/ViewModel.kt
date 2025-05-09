package com.template

import android.app.Activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.template.data.model.ui.Games
import com.template.data.model.ui.Link
import com.template.data.model.ui.Slots
import com.template.data.repository.ImpDataRepository
import com.template.data.repository.ImplAuthUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewModel(private var repository: ImpDataRepository, private var authUserRepository: ImplAuthUserRepository):ViewModel() {

    val pagingDataFlow: Flow<PagingData<Games>> = Pager(PagingConfig(pageSize = 10)) {
        PagingSourseGames(repository.getFirebaseDatabase())
    }.flow.cachedIn(viewModelScope)
    val userData= authUserRepository.getObserverUser()
    val stateSplash = authUserRepository.viewState
   val games: LiveData<List<Games>> = repository.getObserverGames()
   val banner:LiveData<List<Link>> = repository.getObserverBanner()
    val slots:LiveData<List<Slots>> = repository.getObserverSlots()

        init {
           viewModelScope.launch {
               authUserRepository.getDatabaseUser()
            }
            viewModelScope.launch {
               repository.getDatabaseBanner()
            }
            viewModelScope.launch {
                repository.getDatabaseGames()
            }
            viewModelScope.launch {
                repository.getDatabaseSlot()
            }

        }
    fun handleIntent(intent: AuthIntent) {
                when (intent) {
                    is AuthIntent.CheckUser -> authUserRepository.checkUser()
                    is AuthIntent.AuthUsers -> authUsers(intent.email, intent.password,intent.activity)

                }
            }

 private  fun authUsers(email: String, password: String, activity: Activity){
     viewModelScope.launch {
         authUserRepository.authUsers(email, password, activity)
     }

    }





}