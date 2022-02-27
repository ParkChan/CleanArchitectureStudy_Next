package com.chan.movie.ui.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chan.movie.BR
import com.chan.movie.R
import com.chan.movie.databinding.FragmentSearchListBinding
import com.chan.movie.domain.data.Item
import com.chan.movie.ui.common.ext.textInputAsFlow
import com.chan.movie.ui.main.MovieSearchViewModel
import com.chan.movie.ui.main.data.ProgressItem
import com.chan.ui.BaseFragment
import com.chan.ui.adapter.BaseAdapter
import com.chan.ui.adapter.BaseListAdapter
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class SearchListFragment : BaseFragment<FragmentSearchListBinding>(
    FragmentSearchListBinding::inflate
) {
    private val viewModel by activityViewModels<MovieSearchViewModel>()
    private val progressAdapter: BaseAdapter<ProgressItem> by lazy {
        BaseAdapter(
            layoutResourceId = R.layout.rv_progress_item,
            viewHolderBindingId = BR.progressItem,
            mapOf(),
        )
    }
    private val baseAdapter: BaseListAdapter<Item> by lazy {
        BaseListAdapter(
            layoutResourceId = R.layout.rv_search_item,
            viewHolderBindingId = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel),
            object : DiffUtil.ItemCallback<Item>() {
                override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                    oldItem.title == newItem.title

                override fun areContentsTheSame(
                    oldItem: Item, newItem: Item
                ): Boolean = oldItem == newItem
            }
        )
    }
    private val concatAdapter: ConcatAdapter by lazy {
        ConcatAdapter(baseAdapter, progressAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
        initPagingListener()
        initViewModelObserve()
        initTextChangedListener()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
    }

    private fun initTextChangedListener() {
        binding.etInput.textInputAsFlow()
            .debounce(INTERVAL_KEYWORD_SEARCH).onEach {
                viewModel.searchMovies(it.toString())
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initRecyclerView() {
        binding.rvContent.adapter = concatAdapter
    }

    private fun initPagingListener() {
        val layoutManager = binding.rvContent.layoutManager as LinearLayoutManager
        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisiblePosition: Int = layoutManager.findLastVisibleItemPosition()
                val totalCount: Int = concatAdapter.itemCount - 1
                val isScrollEnd = !recyclerView.canScrollVertically(1)

                if (isScrollEnd && lastVisiblePosition >= totalCount) {
                    viewModel.moreMovies()
                }
            }
        })
    }

    private fun initViewModelObserve() {
        viewModel.bottomProgress.observe(viewLifecycleOwner, { isShow ->
            if (isShow) {
                if (progressAdapter.itemCount == BOTTOM_PROGRESSBAR_COUNT) {
                    progressAdapter.replaceItems(listOf(ProgressItem()))
                }
            } else {
                if (progressAdapter.itemCount > BOTTOM_PROGRESSBAR_COUNT) {
                    progressAdapter.replaceItems(emptyList())
                }
            }
        })

        viewModel.movies.observe(viewLifecycleOwner, {
            baseAdapter.submitList(it)
        })
    }

    companion object {
        private const val BOTTOM_PROGRESSBAR_COUNT = 0
        private const val INTERVAL_KEYWORD_SEARCH = 800L
        fun newInstance(): SearchListFragment = SearchListFragment()
    }
}