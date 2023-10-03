package com.template.koin

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.template.DomainRepository
import org.koin.dsl.module

val firebaseModule= module {
    single { DomainRepository(providesFirebaseConfig(), get(), get()) }

}
fun providesFirebaseConfig():FirebaseRemoteConfig{
    return FirebaseRemoteConfig.getInstance()
}