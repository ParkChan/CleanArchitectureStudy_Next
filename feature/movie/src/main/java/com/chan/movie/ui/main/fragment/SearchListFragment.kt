package com.chan.movie.ui.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chan.movie.BR
import com.chan.movie.R
import com.chan.movie.databinding.FragmentSearchListBinding
import com.chan.movie.domain.dto.ItemDto
import com.chan.movie.ui.main.MovieSearchViewModel
import com.chan.movie.ui.main.data.ProgressItem
import com.chan.ui.BaseFragment
import com.chan.ui.adapter.BaseAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class SearchListFragment : BaseFragment<FragmentSearchListBinding>(
    FragmentSearchListBinding::inflate
) {
    private val viewModel by activityViewModels<MovieSearchViewModel>()
    private lateinit var progressAdapter: BaseAdapter<ProgressItem>
    private lateinit var baseAdapter: BaseAdapter<ItemDto>
    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initRecyclerView()
        initPagingListener()
        initViewModelObserve()
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            initTextChangedListener()
        }
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
    }

    private fun initTextChangedListener() {
        binding.etInput.doAfterTextChanged { text ->
            lifecycleScope.launch {
                val inputText = text.toString()
                viewModel.searchMovies(inputText)
            }
        }
    }

    private fun initRecyclerView() {
        progressAdapter = BaseAdapter(
            layoutResourceId = R.layout.rv_progress_item,
            viewHolderBindingId = BR.progressItem,
            mapOf()
        )
        baseAdapter = BaseAdapter(
            layoutResourceId = R.layout.rv_search_item,
            viewHolderBindingId = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel)
        )
        concatAdapter = ConcatAdapter(baseAdapter, progressAdapter)
        binding.rvContent.adapter = concatAdapter
    }

    private fun initPagingListener() {
        val layoutManager = binding.rvContent.layoutManager as LinearLayoutManager
        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisiblePosition: Int = layoutManager.findLastVisibleItemPosition()
                val totalCount: Int = binding.rvContent.adapter!!.itemCount - 1
                val isScrollEnd = !recyclerView.canScrollVertically(1)

                Timber.d("lastVisiblePosition >> $lastVisiblePosition item count >> ${binding.rvContent.adapter!!.itemCount}")
                if (isScrollEnd && lastVisiblePosition >= totalCount) {
                    viewModel.moreMovies(binding.etInput.text.toString())
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModelObserve() {
        viewModel.progressData.observe(viewLifecycleOwner, {
            if (progressAdapter.itemCount == BOTTOM_PROGRESSBAR_COUNT || it.isEmpty()) {
                progressAdapter.replaceItems(it)
                progressAdapter.notifyDataSetChanged()
            }
        })

        viewModel.movies.observe(viewLifecycleOwner, {
            baseAdapter.replaceItems(it)
            baseAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        private const val BOTTOM_PROGRESSBAR_COUNT = 0
        fun newInstance(): SearchListFragment = SearchListFragment()
    }
}