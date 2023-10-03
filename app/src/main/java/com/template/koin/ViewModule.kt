package com.template.koin

import com.template.DomainRepository
import com.template.DomainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleViewModel= module {
    single <DomainRepository>{ DomainRepository(get(),get(),get()) }
    viewModel { DomainViewModel(get()) }
}