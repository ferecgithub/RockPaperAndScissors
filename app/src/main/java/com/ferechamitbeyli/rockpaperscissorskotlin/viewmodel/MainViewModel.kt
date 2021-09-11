package com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel

import androidx.lifecycle.*
import com.ferechamitbeyli.rockpaperscissorskotlin.model.MOVES
import com.ferechamitbeyli.rockpaperscissorskotlin.model.STATE

class MainViewModel : ViewModel() {

    companion object {
        private var staticTurn = 0
        private var staticPlayerMove : String = ""
        private var staticComputerMove : String = ""
        private var staticPlayerPoints = 0
        private var staticComputerPoints = 0
    }

    private var _playerPoints = MutableLiveData<Int>(0)
    val playerPoint : LiveData<Int> = _playerPoints

    private var _computerPoints = MutableLiveData<Int>(0)
    val computerPoint : LiveData<Int> = _computerPoints

    private var _playerMove = MutableLiveData<String>("")
    val playerMove : LiveData<String> = _playerMove

    private var _computerMove = MutableLiveData<String>("")
    val computerMove : LiveData<String> = _computerMove

    private var _turnNr = MutableLiveData<Int>(1)
    val turnNr : LiveData<Int> = _turnNr


    /**  ---------------- Caching Functions ----------------  **/

    private fun storePoints(playerPoints: Int, computerPoints: Int)  {
        staticPlayerPoints = playerPoints
        staticComputerPoints = computerPoints
    }

    fun storeMoves(playerMove: String, computerMove: String) {
        staticPlayerMove = playerMove
        staticComputerMove = computerMove
    }

    fun getPlayerMove() {
        _playerMove.value = staticPlayerMove
    }

    fun getComputerMove() {
        _computerMove.value = staticComputerMove
    }

    fun getPlayerPoints() {
        _playerPoints.value = staticPlayerPoints
    }

    fun getComputerPoints() {
        _computerPoints.value = staticComputerPoints
    }

    fun getTurn() {
        _turnNr.value = staticTurn
    }

    fun resetAllValues() {
        staticTurn = 0
        staticPlayerMove = ""
        staticComputerMove = ""
        staticPlayerPoints = 0
        staticComputerPoints = 0
    }


    fun randomlySelectComputerMove(): String = MOVES.values().random().name

    /**  ---------------- Gameplay Functions ----------------  **/

    fun incrementTurn(): Int = staticTurn++

    fun increasePlayerPoint() {
        staticPlayerPoints += 1
    }

    fun increaseComputerPoint() {
        staticComputerPoints += 1
    }

    fun determineTheWinner(): STATE = when {
        staticComputerPoints == staticPlayerPoints -> {
            STATE.DRAW
        }
        staticComputerPoints > staticPlayerPoints -> {
            STATE.LOST
        }
        else -> {
            STATE.WON
        }
    }




}