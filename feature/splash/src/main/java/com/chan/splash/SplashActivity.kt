package com.chan.splash

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.chan.splash.databinding.ActivitySplashBinding
import com.chan.ui.BaseActivity
import com.chan.util.startActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(SPLASH_DELAY_TIME)
            startActivity(BuildConfig.MAIN_ACTIVITY)
            finish()
        }
    }

    companion object {
        private const val SPLASH_DELAY_TIME = 2_000L
    }
}
