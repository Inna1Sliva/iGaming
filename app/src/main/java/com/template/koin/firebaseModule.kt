package com.template.koin

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.template.data.repository.ImpDataRepository
import com.template.data.repository.ImplAuthUserRepository
import org.koin.dsl.module

val firebaseModule= module {
      single { ImpDataRepository(providesFirebaseDatabase()) }
    single { ImplAuthUserRepository(providesFirebaseDatabaseUser(),providesFirebaseAuth()) }

}
fun providesFirebaseAuth(): FirebaseAuth {
    return Firebase.auth
}

fun providesFirebaseDatabaseUser():FirebaseDatabase{
   return FirebaseDatabase.getInstance()
}


fun providesFirebaseDatabase():FirebaseDatabase{
    return  FirebaseDatabase.getInstance()
}

