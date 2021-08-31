package com.ferechamitbeyli.rockpaperscissorskotlin.view.activities.main_activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ferechamitbeyli.rockpaperscissorskotlin.R
import com.ferechamitbeyli.rockpaperscissorskotlin.databinding.FragmentPickBinding
import com.ferechamitbeyli.rockpaperscissorskotlin.model.MOVES
import com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel.MainViewModel

class PickScreenFragment : BaseFragment<MainViewModel, FragmentPickBinding>() {

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPickBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val randomComputerMove = viewModel.randomlySelectComputerMove()

        binding.moveRockIv.setOnClickListener {
            viewModel.storeMoves(MOVES.ROCK.name, randomComputerMove)
            findNavController().navigate(R.id.action_pickScreenFragment_to_gameScreenFragment)
        }

        binding.movePaperIv.setOnClickListener {
            viewModel.storeMoves(MOVES.PAPER.name, randomComputerMove)
            findNavController().navigate(R.id.action_pickScreenFragment_to_gameScreenFragment)
        }

        binding.moveScissorsIv.setOnClickListener {
            viewModel.storeMoves(MOVES.SCISSORS.name, randomComputerMove)
            findNavController().navigate(R.id.action_pickScreenFragment_to_gameScreenFragment)
        }

    }



}