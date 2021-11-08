package com.chan.moviesearcher.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
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

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel by viewModels<MovieSearchViewModel>()
    private var job: Job? = null
    private val watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            job?.cancel()
            job = lifecycleScope.launch {
                val inputText = s.toString()
                if (inputText.isNotEmpty()) {
                    delay(INTERVAL_KEYWORD_SEARCH)
                    viewModel.getMovieList(query = inputText)
                } else {
                    viewModel.clearMovieList()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initRecyclerView()
    }

    override fun onPostResume() {
        super.onPostResume()
        initViewModelObserve()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
    }

    private fun initViewModelObserve() {
        binding.etInput.addTextChangedListener(watcher)
    }

    private fun initRecyclerView() {
        binding.rvMainContent.adapter = BaseAdapter<ItemDto>(
            layoutResourceId = R.layout.rv_main_item,
            viewHolderBindingId = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel)
        )
    }

    override fun onPause() {
        super.onPause()
        binding.etInput.removeTextChangedListener(watcher)
    }

    companion object {
        private const val INTERVAL_KEYWORD_SEARCH = 500L
    }

}