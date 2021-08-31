package com.ferechamitbeyli.rockpaperscissorskotlin.model.repository.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.ferechamitbeyli.rockpaperscissorskotlin.util.Constants.CACHE_KEY_FOR_COMPUTER_MOVE
import com.ferechamitbeyli.rockpaperscissorskotlin.util.Constants.CACHE_KEY_FOR_COMPUTER_POINTS
import com.ferechamitbeyli.rockpaperscissorskotlin.util.Constants.CACHE_KEY_FOR_PLAYER_POINTS
import com.ferechamitbeyli.rockpaperscissorskotlin.util.Constants.CACHE_KEY_FOR_SELECTED_MOVE
import com.ferechamitbeyli.rockpaperscissorskotlin.util.Constants.CACHE_NAME
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreObject(val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(CACHE_NAME)
        val selectedMove_cached = stringPreferencesKey(CACHE_KEY_FOR_SELECTED_MOVE)
        val computerMove_cached = stringPreferencesKey(CACHE_KEY_FOR_COMPUTER_MOVE)
        val playerScore_cached = intPreferencesKey(CACHE_KEY_FOR_PLAYER_POINTS)
        val computerScore_cached = intPreferencesKey(CACHE_KEY_FOR_COMPUTER_POINTS)
    }

    suspend fun storeMove(playerMove: String, computerMove: String) {

        context.dataStore.edit {
            it[selectedMove_cached] = playerMove
            it[computerMove_cached] = computerMove
        }
    }

    suspend fun storePoints(playerPoints: Int, computerPoints: Int) {

        context.dataStore.edit {
            it[playerScore_cached] = playerPoints
            it[computerScore_cached] = computerPoints
        }

    }

    fun getPlayerMove() =  context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }
        .map {
        it[selectedMove_cached] ?: ""
    }

    fun getComputerMove() = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }
        .map {
        it[computerMove_cached] ?: ""
    }


    fun getPlayerPoints() = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }
        .map {
        it[playerScore_cached] ?: 0
    }

    fun getComputerPoints() = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }
        .map {
        it[computerScore_cached] ?: 0
    }

    suspend fun deleteAllCache() = context.dataStore.edit {
        it.clear()
    }

}