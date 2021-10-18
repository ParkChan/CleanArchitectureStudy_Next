package com.chan.moviesearcher.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.chan.moviesearcher.BR
import com.chan.moviesearcher.R
import com.chan.moviesearcher.databinding.ActivityMainBinding
import com.chan.moviesearcher.domain.dto.ItemDto
import com.chan.ui.BaseActivity
import com.chan.ui.adapter.BaseAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel by viewModels<MovieSearchViewModel>()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initViewModelObserve()
        initRecyclerView()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
    }

    private fun initViewModelObserve() {
        viewModel.query.observe(this, {
            job?.cancel()
            job = lifecycleScope.launch {
                if (it.isNotEmpty()) {
                    delay(INTERVAL_KEYWORD_SEARCH)
                    viewModel.getMovieList(query = it)
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvMainContent.adapter = BaseAdapter<ItemDto>(
            layoutResourceId = R.layout.rv_main_item,
            viewHolderBindingId = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel)
        )
    }

    companion object {
        private const val INTERVAL_KEYWORD_SEARCH = 500L
    }

}