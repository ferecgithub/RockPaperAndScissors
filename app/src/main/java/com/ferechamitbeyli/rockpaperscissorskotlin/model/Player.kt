package com.ferechamitbeyli.rockpaperscissorskotlin.model

data class Player(
    private var score: Int = 0,
    private var selectedMove: MOVES?,
    private var hasWon: Boolean = false
)

enum class MOVES {
    ROCK, PAPER, SCISSORS
}

enum class STATE {
    WON, LOST, DRAW
}