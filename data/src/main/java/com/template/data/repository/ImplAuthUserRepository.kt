package com.template.data.repository


import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.template.data.model.auth.AuthViewState
import com.template.data.model.auth.User
import com.template.data.model.ui.Games
import com.template.data.model.ui.Slots
import com.template.domain.repository.AuthUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class ImplAuthUserRepository( private val database: FirebaseDatabase, private var firebaseAuth: FirebaseAuth):AuthUserRepository {
    private val _viewState = MutableStateFlow(AuthViewState())
    val viewState: StateFlow<AuthViewState> get() = _viewState
    private var user = MutableLiveData<List<User>>()

    init {
        user = MutableLiveData()
    }
     suspend fun authUsers(email: String, password: String, activity: Activity) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    user?.uid?.toString()?.let { saveUserAuthDatabase(it) }
                       Toast.makeText(activity, "Регистрация успешна", Toast.LENGTH_SHORT).show()


                } else {
                      // Toast.makeText(this, "Ошибка регистрации: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

  private fun saveUserAuthDatabase(uuid: String) {
      val hashMap: HashMap<String, String> = HashMap()
      hashMap.put("check", "1000")
      hashMap.put("cards", "")
      hashMap.put("setting", "")
         database.reference.child("userUUID").child(uuid).setValue(hashMap)
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     // Успешно сохранено в базе данных.
                    // saveUserToFirestore(userUUID, phoneNumber) // Сохраняем пользователя в Firestore.
                 } else {
                     // Обработка ошибки.
                 }
             }
    }

    override suspend fun getDatabaseUser() {
        val uuid = firebaseAuth.currentUser?.uid
        try {
            var list = mutableListOf<User>()
            val snapshot = database.getReference("userUUID").child(uuid!!).get().await()
            if (snapshot.exists()){

                for (dataSnaphot in snapshot.children){
                    val user = dataSnaphot.getValue(User::class.java)
                    if (user != null) {
                        list.add(user)
                    }
                }

            }
          user.postValue(list)

        }catch (e:Exception){
            Log.d("FIREBAS", "${e}")
        }
    }
    fun getObserverUser():MutableLiveData<List<User>>{
        return user
    }

    override fun checkUser() {
        val user =firebaseAuth.currentUser
        if (user != null) {
            // Пользователь зарегистрирован, переходим на главный экран
            _viewState.value = _viewState.value.copy(
                isLoading = true,
                )
        } else {
            // Пользователь не зарегистрирован, переходим на экран регистрации
            _viewState.value = _viewState.value.copy(
                isLoading = false,
                errorMessage = "Failed to save user"
            )
        }
    }

}