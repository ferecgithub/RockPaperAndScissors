package com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ferechamitbeyli.rockpaperscissorskotlin.model.repository.Repository
import com.ferechamitbeyli.rockpaperscissorskotlin.model.repository.RepositoryImpl
import com.ferechamitbeyli.rockpaperscissorskotlin.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when{

        modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository as RepositoryImpl) as T

        else -> throw IllegalArgumentException("ViewModelClass Not Found")
    }

}