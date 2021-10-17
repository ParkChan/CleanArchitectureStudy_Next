package com.chan.moviesearcher.ui

import androidx.activity.viewModels
import com.chan.moviesearcher.databinding.ActivityMainBinding
import com.chan.moviesearcher.ui.viewmodel.MovieSearchViewModel
import com.chan.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MovieSearchViewModel by viewModels()

    override fun bindViewModel() {
    }

    override fun setupObserve() {
    }
}