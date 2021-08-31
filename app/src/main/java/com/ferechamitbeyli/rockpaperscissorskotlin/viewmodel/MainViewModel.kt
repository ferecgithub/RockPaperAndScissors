package com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ferechamitbeyli.rockpaperscissorskotlin.model.MOVES
import com.ferechamitbeyli.rockpaperscissorskotlin.model.STATE
import com.ferechamitbeyli.rockpaperscissorskotlin.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    var repository: Repository
) : ViewModel() {

    private var _playerPoints = MutableStateFlow(0)
    val playerPoints = _playerPoints.asStateFlow()

    private var _computerPoints = MutableStateFlow(0)
    val computerPoints = _computerPoints.asStateFlow()

    private var _playerMove = MutableStateFlow("")
    val playerMove = _playerMove.asStateFlow()

    private var _computerMove = MutableStateFlow("")
    val computerMove = _computerMove.asStateFlow()

    /*

    private var _playerPoints = MutableLiveData<Int>(0)
    val playerPoints : LiveData<Int> = _playerPoints

    private var _computerPoints = MutableLiveData<Int>(0)
    val computerPoints : LiveData<Int> = _computerPoints

    private var _playerMove = MutableLiveData<String>()
    val playerMove : LiveData<String> = _playerMove

    private var _computerMove = MutableLiveData<String>()
    val computerMove : LiveData<String> = _computerMove

     */

    /**  ---------------- Caching Functions ----------------  **/

    private fun storePoints(playerPoints: Int, computerPoints: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.storePoints(playerPoints, computerPoints)
        Log.d("STORE_POINTS", "(MAINVIEWMODEL) POINTS: $playerPoints , $computerPoints")
    }

    fun storeMoves(playerMove: String, computerMove: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.storeMoves(playerMove, computerMove)
        Log.d("STORE_MOVES", "(MAINVIEWMODEL) MOVES: $playerMove , $computerMove")
    }

    fun getPlayerMove() = viewModelScope.launch {
        repository.getPlayerMove().collect {
            _playerMove.value = it
            Log.d("GOT_PLAYER_MOVE", "(MAINVIEWMODEL) Player: $it")
        }
    }


    fun getComputerMove() = viewModelScope.launch {
        repository.getComputerMove().collect {
            _computerMove.value = it
            Log.d("GOT_COMPUTER_MOVE", "(MAINVIEWMODEL) Computer: $it")
        }
    }

    fun getPlayerPoints() = viewModelScope.launch {
        repository.getPlayerPoints().collect {
            _playerPoints.value = it
            Log.d("GOT_PLAYER_POINTS", "(MAINVIEWMODEL) Player: $it")
        }
    }

    fun getComputerPoints() = viewModelScope.launch {
        repository.getComputerPoints().collect {
            _computerPoints.value = it
            Log.d("GOT_COMPUTER_POINTS", "(MAINVIEWMODEL) Computer: $it")
        }
    }

    fun deleteAllCache() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllCache()
    }

    fun randomlySelectComputerMove(): String = MOVES.values().random().name

    /**  ---------------- Gameplay Functions ----------------  **/

    fun increasePlayerPoint() = viewModelScope.launch {
        _playerPoints.value = _computerPoints.value.plus(1)
        withContext((Dispatchers.IO)) {
            storePoints(_playerPoints.value, _computerPoints.value)
        }
        Log.d("INCR_PLAYER_POINTS", "(MAINVIEWMODEL) Player: ${_playerPoints.value} , Comp: ${_computerPoints.value}")
    }

    fun increaseComputerPoint() = viewModelScope.launch {
        _computerPoints.value = _computerPoints.value.plus(1)
        withContext((Dispatchers.IO)) {
            storePoints(_playerPoints.value, _computerPoints.value)
        }

        Log.d("INCR_COMPUTER_POINTS", "(MAINVIEWMODEL) Player: ${_playerPoints.value} , Comp: ${_computerPoints.value}")
    }

    fun determineTheWinner(): STATE = when {
        _computerPoints.value == _playerPoints.value -> {
            STATE.DRAW
        }
        _computerPoints.value > _playerPoints.value -> {
            STATE.LOST
        }
        else -> {
            STATE.WON
        }
    }




}