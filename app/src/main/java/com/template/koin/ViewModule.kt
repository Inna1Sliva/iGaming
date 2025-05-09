package com.template.koin

import com.template.ViewModel
import com.template.data.repository.ImpDataRepository
import com.template.data.repository.ImplAuthUserRepository
import com.template.domain.repository.DataRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleViewModel= module {
    single <DataRepository>{ ImpDataRepository(get()) }
    single { ImplAuthUserRepository(get(), get())}
    viewModel { ViewModel(get(),get()) }
}