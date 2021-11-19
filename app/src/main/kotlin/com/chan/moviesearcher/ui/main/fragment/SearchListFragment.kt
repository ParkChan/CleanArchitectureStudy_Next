package com.chan.moviesearcher.ui.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chan.moviesearcher.BR
import com.chan.moviesearcher.R
import com.chan.moviesearcher.databinding.FragmentSearchListBinding
import com.chan.moviesearcher.domain.dto.ItemDto
import com.chan.moviesearcher.ui.main.MovieSearchViewModel
import com.chan.moviesearcher.ui.main.data.HeaderData
import com.chan.ui.BaseFragment
import com.chan.ui.adapter.BaseAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchListFragment : BaseFragment<FragmentSearchListBinding>(
    FragmentSearchListBinding::inflate
) {
    private val viewModel by activityViewModels<MovieSearchViewModel>()
    private lateinit var headerAdapter: BaseAdapter<HeaderData>
    private lateinit var baseAdapter: BaseAdapter<ItemDto>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initRecyclerView()
        initPagingListener()
        initViewModelObserve()
    }

    override fun onResume() {
        super.onResume()
        initTextChangedListener()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
    }

    private fun initTextChangedListener() {
        binding.etInput.doAfterTextChanged { text ->
            lifecycleScope.launch {
                val inputText = text.toString()
                if (inputText.isNotBlank()) {
                    viewModel.setSearchQuery(inputText)
                } else {
                    viewModel.clearData()
                }
            }
        }
    }

    private fun initRecyclerView() {
        headerAdapter = BaseAdapter(
            layoutResourceId = R.layout.rv_header_item,
            viewHolderBindingId = BR.headerItem,
            mapOf()
        )
        baseAdapter = BaseAdapter(
            layoutResourceId = R.layout.rv_search_item,
            viewHolderBindingId = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel)
        )
        binding.rvContent.adapter = ConcatAdapter(headerAdapter, baseAdapter)
    }

    private fun initPagingListener() {
        val layoutManager = binding.rvContent.layoutManager as GridLayoutManager
        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisiblePosition: Int = layoutManager.findLastVisibleItemPosition()
                val totalCount: Int = baseAdapter.itemCount - 1
                val isScrollEnd = !recyclerView.canScrollVertically(1)

                if (isScrollEnd && lastVisiblePosition >= totalCount) {
                    viewModel.moreMovieList(binding.etInput.text.toString())
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModelObserve() {
        viewModel.headers.observe(viewLifecycleOwner, {
            headerAdapter.replaceItems(it)
            headerAdapter.notifyDataSetChanged()
        })
        viewModel.movies.observe(viewLifecycleOwner, {
            baseAdapter.replaceItems(it)
            baseAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        fun newInstance(): SearchListFragment = SearchListFragment()
    }
}