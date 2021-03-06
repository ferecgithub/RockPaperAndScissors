package com.ferechamitbeyli.rockpaperscissorskotlin.view.activities.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ferechamitbeyli.rockpaperscissorskotlin.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fcv)
                as NavHostFragment
        navController = navHostFragment.navController

    }
}