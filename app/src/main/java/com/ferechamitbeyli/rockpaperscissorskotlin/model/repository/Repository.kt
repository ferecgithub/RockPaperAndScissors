package com.ferechamitbeyli.rockpaperscissorskotlin.model.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getPlayerMove(): Flow<String>
    fun getComputerMove(): Flow<String>
    fun getPlayerPoints() : Flow<Int>
    fun getComputerPoints() : Flow<Int>
    suspend fun storeMoves(playerMove: String, computerMove: String)
    suspend fun storePoints(playerPoints: Int, computerPoints: Int)
    suspend fun deleteAllCache(): Preferences
}