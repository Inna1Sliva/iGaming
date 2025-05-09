package com.template.domain.repository

import com.template.domain.model.Games

interface DataRepository {
    suspend fun getDatabaseSlot()
    suspend fun getDatabaseBanner()
    suspend fun getDatabaseGames()
}