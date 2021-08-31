package com.ferechamitbeyli.rockpaperscissorskotlin.view.activities.main_activity.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ferechamitbeyli.rockpaperscissorskotlin.R
import com.ferechamitbeyli.rockpaperscissorskotlin.databinding.FragmentGameScreenBinding
import com.ferechamitbeyli.rockpaperscissorskotlin.model.MOVES
import com.ferechamitbeyli.rockpaperscissorskotlin.model.STATE
import com.ferechamitbeyli.rockpaperscissorskotlin.model.repository.RepositoryImpl
import com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameScreenFragment : BaseFragment<MainViewModel, FragmentGameScreenBinding, RepositoryImpl>()  {

    companion object {
        var staticTurn = 0
        var staticPlayerMove : String? = null
        var staticComputerMove : String? = null
        var staticPlayerPoints = 0
        var staticComputerPoints = 0
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGameScreenBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = RepositoryImpl(requireActivity())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        getMoves()
        getPoints()
         */

        populateTheFragment()

    }

    private fun checkIfGameIsOver() : Boolean = staticTurn >= 3

    private fun getMoves() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        viewModel.getComputerMove()
        viewModel.computerMove.collect {
            staticComputerMove = it
        }

        viewModel.getPlayerMove()
        viewModel.playerMove.collect {
            staticPlayerMove = it
        }

        Log.d("GET_MOVES", "(GAMESCREEN) GET_MOVES = Player: $staticPlayerMove , Computer: $staticComputerMove" )
    }

    private fun getPoints() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        viewModel.getComputerPoints()
        viewModel.computerPoints.collect {
            staticComputerPoints = it
        }

        viewModel.getPlayerPoints()
        viewModel.playerPoints.collect {
            staticPlayerPoints = it
        }

        Log.d("GET_POINTS", "(GAMESCREEN) GET_POINTS = Player: $staticPlayerPoints , Computer: $staticComputerPoints" )

        binding.computerPointsTv.text = staticComputerPoints.toString()
        binding.playerPointsTv.text = staticPlayerPoints.toString()

    }

    /**
     * If the game is over then it gets the points and determines who is the winner then
     * deletes the whole cache.
     * If the game isn't over, it increments turnNumber and compares moves.
     *
     */

    private fun populateTheFragment() {

        Log.d("TURN (GAMESCREEN)", "TURN = $staticTurn" )

        if (checkIfGameIsOver()) {

            getPoints()

            binding.playAgainBtn.text = resources.getString(R.string.play_again)
            binding.playerPointsTv.text = staticPlayerPoints.toString()
            binding.computerPointsTv.text = staticComputerPoints.toString()

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
            viewModel.deleteAllCache()
            staticTurn = 0

            binding.playAgainBtn.setOnClickListener {
                findNavController().navigate(R.id.action_gameScreenFragment_to_pickScreenFragment)
            }

        } else {

            staticTurn++

            getMoves()

            Log.d("GET_MOVES_INSIDE", "(GAMESCREEN) GET_MOVES = Player: $staticPlayerMove , Computer: $staticComputerMove" )

            when (staticPlayerMove) {

                /**
                 * When I play Rock, if computer plays rock it's a draw.
                 * If computer plays paper, it's a lose.
                 * If computer plays scissors, it's a win.
                 */

                MOVES.ROCK.name -> {
                    binding.playerHandIv.setImageResource(R.drawable.big_hand_rock_player)
                    when (staticComputerMove) {
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
                 * When I play Paper, if computer plays rock it's a win.
                 * If computer plays paper, it's a draw.
                 * If computer plays scissors, it's a lose.
                 */

                MOVES.PAPER.name -> {
                    binding.playerHandIv.setImageResource(R.drawable.big_hand_paper_player)
                    when (staticComputerMove) {
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
                 * When I play Scissors, if computer plays rock it's a lose.
                 * If computer plays paper, it's a win.
                 * If computer plays scissors, it's a draw.
                 */

                else -> {

                    binding.playerHandIv.setImageResource(R.drawable.big_hand_scissors_player)
                    when (staticComputerMove) {
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



}