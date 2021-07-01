package com.devventure.clonezap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devventure.clonezap.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {
    val splashViewModel = SplashViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel.loadView()
    }
}