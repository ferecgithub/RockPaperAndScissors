package com.ferechamitbeyli.rockpaperscissorskotlin.view.activities.main_activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ferechamitbeyli.rockpaperscissorskotlin.R
import com.ferechamitbeyli.rockpaperscissorskotlin.databinding.FragmentGameScreenBinding
import com.ferechamitbeyli.rockpaperscissorskotlin.model.MOVES
import com.ferechamitbeyli.rockpaperscissorskotlin.model.STATE
import com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class GameScreenFragment : BaseFragment<MainViewModel, FragmentGameScreenBinding>() {

    companion object {
        private var computerMove = ""
        private var playerMove = ""
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGameScreenBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMoves()
        getPoints()

        binding.playAgainBtn.setOnClickListener {
            populateAll()
        }

    }

    private fun checkIfGameIsOver(): Boolean {
        viewModel.getTurn()

        val result = viewModel.turnNr.value?.let {
            it > 3
        } ?: false

        return result
    }

    private fun getMoves() = viewLifecycleOwner.lifecycleScope.launch {

        viewModel.getComputerMove()
        viewModel.computerMove.observe(viewLifecycleOwner, {
            computerMove = it
        })

        viewModel.getPlayerMove()
        viewModel.playerMove.observe(viewLifecycleOwner, {
            playerMove = it
        })

    }

    private fun getPoints() = viewLifecycleOwner.lifecycleScope.launch {

        viewModel.getComputerPoints()
        viewModel.computerPoint.observe(viewLifecycleOwner, {
            binding.computerPointsTv.text = it.toString()
        })

        viewModel.getPlayerPoints()
        viewModel.playerPoint.observe(viewLifecycleOwner, {
            binding.playerPointsTv.text = it.toString()
        })

    }

    /**
     * If the game is over then it gets the points and determines who is the winner then
     * deletes the whole cache.
     * If the game isn't over, it increments turnNumber and compares moves.
     *
     */

    private fun populateAll() {

        getMoves()
        getPoints()

        if (checkIfGameIsOver()) {

            populateGameContinues()
            populateGameFinished()

        } else {

            populateGameContinues()

        }

    }

    private fun populateGameFinished() {

        binding.playAgainBtn.text = resources.getString(R.string.play_again)

        when (viewModel.determineTheWinner()) {
            STATE.WON -> {
                binding.gameStateIv.setImageResource(R.drawable.its_win)
            }
            STATE.DRAW -> {
                binding.gameStateIv.setImageResource(R.drawable.its_draw)
            }
            else -> {
                binding.gameStateIv.setImageResource(R.drawable.its_lose)
            }
        }

        binding.gameStateIv.visibility = View.VISIBLE


        binding.playAgainBtn.setOnClickListener {
            viewModel.resetAllValues()
            resetAllStaticValues()
            findNavController().navigate(R.id.action_gameScreenFragment_to_pickScreenFragment)
        }

    }

    private fun resetAllStaticValues() {
        computerMove = ""
        playerMove = ""
    }

    private fun populateGameContinues() {

        viewModel.incrementTurn()

        when (playerMove) {

            /**
             * When the player plays Rock, if computer plays rock it's a draw.
             * If computer plays paper, it's a lose.
             * If computer plays scissors, it's a win.
             */

            MOVES.ROCK.name -> {
                binding.playerHandIv.setImageResource(R.drawable.big_hand_rock_player)
                when (computerMove) {
                    MOVES.ROCK.name -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_rock)
                    }
                    MOVES.PAPER.name -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_paper)
                        viewModel.increaseComputerPoint()
                    }
                    else -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_scissors)
                        viewModel.increasePlayerPoint()
                    }
                }
            }

            /**
             * When the player plays Paper, if computer plays rock it's a win.
             * If computer plays paper, it's a draw.
             * If computer plays scissors, it's a lose.
             */

            MOVES.PAPER.name -> {
                binding.playerHandIv.setImageResource(R.drawable.big_hand_paper_player)
                when (computerMove) {
                    MOVES.ROCK.name -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_rock)
                        viewModel.increasePlayerPoint()
                    }
                    MOVES.PAPER.name -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_paper)
                    }
                    else -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_scissors)
                        viewModel.increaseComputerPoint()
                    }
                }

            }

            /**
             * When the player plays Scissors, if computer plays rock it's a lose.
             * If computer plays paper, it's a win.
             * If computer plays scissors, it's a draw.
             */

            else -> {

                binding.playerHandIv.setImageResource(R.drawable.big_hand_scissors_player)
                when (computerMove) {
                    MOVES.ROCK.name -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_rock)
                        viewModel.increaseComputerPoint()
                    }
                    MOVES.PAPER.name -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_paper)
                        viewModel.increasePlayerPoint()
                    }
                    else -> {
                        binding.computerHandIv.setImageResource(R.drawable.big_hand_scissors)
                    }
                }

            }
        }

        getPoints()

        binding.playAgainBtn.text = resources.getString(R.string.next_round)

        binding.playAgainBtn.setOnClickListener {
            findNavController().navigate(R.id.action_gameScreenFragment_to_pickScreenFragment)
        }

    }


}