package com.chan.splash

import androidx.lifecycle.lifecycleScope
import com.chan.splash.databinding.ActivitySplashBinding
import com.chan.ui.BaseActivity
import com.chan.util.startActivity
import kotlinx.coroutines.delay

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    init {
        lifecycleScope.launchWhenCreated {
            delay(SPLASH_DELAY_TIME)
            startActivity("com.chan.movie.ui.main.MainActivity")
            finish()
        }
    }

    companion object{
        private const val SPLASH_DELAY_TIME = 2_000L
    }
}
