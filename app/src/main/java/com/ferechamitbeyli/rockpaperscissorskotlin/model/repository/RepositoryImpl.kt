package com.ferechamitbeyli.rockpaperscissorskotlin.model.repository

import android.content.Context
import com.ferechamitbeyli.rockpaperscissorskotlin.model.repository.cache.DataStoreObject

class RepositoryImpl(
    val context: Context
) : Repository {

    private val dataStoreObject = DataStoreObject(context)

    override fun getPlayerMove() = dataStoreObject.getPlayerMove()

    override fun getComputerMove() = dataStoreObject.getComputerMove()

    override fun getPlayerPoints() = dataStoreObject.getPlayerPoints()

    override fun getComputerPoints() = dataStoreObject.getComputerPoints()

    override suspend fun storeMoves(playerMove: String, computerMove: String) = dataStoreObject.storeMove(playerMove, computerMove)

    override suspend fun storePoints(playerPoints: Int, computerPoints: Int) = dataStoreObject.storePoints(playerPoints, computerPoints)

    override suspend fun deleteAllCache() = dataStoreObject.deleteAllCache()

}