package com.ferechamitbeyli.rockpaperscissorskotlin.view.activities.main_activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ferechamitbeyli.rockpaperscissorskotlin.model.repository.Repository
import com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel.factories.ViewModelFactory

abstract class BaseFragment<VM: ViewModel, B: ViewBinding, R: Repository> : Fragment() {

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun getFragmentRepository(): R

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = getFragmentBinding(inflater, container)

        val factory = ViewModelFactory(getFragmentRepository())

        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        return binding.root
    }




}