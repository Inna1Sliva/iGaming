package com.template.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.template.domain.repository.DataRepository
import com.template.data.model.ui.Games
import com.template.data.model.ui.Link
import com.template.data.model.ui.Slots
import kotlinx.coroutines.tasks.await

class ImpDataRepository(private var dataReference: FirebaseDatabase
): DataRepository {
    private var games = MutableLiveData<List<Games>>()
    private var banner = MutableLiveData<List<Link>>()
    private var slots = MutableLiveData<List<Slots>>()

    init {
        games = MutableLiveData()
        banner= MutableLiveData()
        slots = MutableLiveData()
    }
    fun getFirebaseDatabase(): FirebaseDatabase {
        return dataReference
    }

 fun getObserverGames(): MutableLiveData<List<Games>> {
        return games
    }
    fun getObserverSlots():MutableLiveData<List<Slots>>{
        return slots
    }
    fun getObserverBanner():MutableLiveData<List<Link>>{
        return banner
    }

    override suspend fun getDatabaseSlot() {
        try {
            val snapshot = dataReference.getReference("slot").get().await()
            if (snapshot.exists()){
                val slotList = mutableListOf<Slots>()
                for (dataSnaphot in snapshot.children){
                    val data = dataSnaphot.getValue(Slots::class.java)
                    slotList.add(data!!)
                }
                slots.postValue(slotList)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override suspend fun getDatabaseBanner() {
        try {
            val snapshot = dataReference.getReference("banner").get().await()
            if (snapshot.exists()){
                val imageList = mutableListOf<Link>()
                for (dataSnaphot in snapshot.children){
                    val data = dataSnaphot.getValue(Link::class.java)
                    Log.d("FIREBAS11", "${data}")
                    imageList.add(data!!)
                }
                banner.postValue(imageList)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override suspend fun getDatabaseGames() {
        try {
            val snapshot = dataReference.getReference("igaming").get().await()
            if ( snapshot.exists()){
                val gamesList = mutableListOf<Games>()
                for (dataSnaphot in snapshot.children){
                    val data = dataSnaphot.getValue(Games::class.java)
                    Log.d("FIREBAS", "${data}")
                    gamesList.add(data!!)
                }
                games.postValue(gamesList)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}