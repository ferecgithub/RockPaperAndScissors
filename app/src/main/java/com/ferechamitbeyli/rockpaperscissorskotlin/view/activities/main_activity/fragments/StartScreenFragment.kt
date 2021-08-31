package com.ferechamitbeyli.rockpaperscissorskotlin.view.activities.main_activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ferechamitbeyli.rockpaperscissorskotlin.R
import com.ferechamitbeyli.rockpaperscissorskotlin.databinding.FragmentStartBinding
import com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel.MainViewModel

class StartScreenFragment : BaseFragment<MainViewModel, FragmentStartBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_startScreenFragment_to_pickScreenFragment)
        }
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentStartBinding.inflate(inflater, container, false)

}