package com.chan.moviesearcher.ui.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.chan.moviesearcher.BR
import com.chan.moviesearcher.R
import com.chan.moviesearcher.databinding.FragmentSaveListBinding
import com.chan.moviesearcher.domain.dto.ItemDto
import com.chan.moviesearcher.ui.main.MovieSearchViewModel
import com.chan.ui.BaseFragment
import com.chan.ui.adapter.BaseAdapter

class SaveListFragment : BaseFragment<FragmentSaveListBinding>(
    FragmentSaveListBinding::inflate
) {
    private val viewModel by activityViewModels<MovieSearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
    }

    private fun initRecyclerView() {
        binding.rvContent.adapter = BaseAdapter<ItemDto>(
            layoutResourceId = R.layout.rv_save_item,
            viewHolderBindingId = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel)
        )
    }

    companion object {
        fun newInstance(): SaveListFragment = SaveListFragment()
    }
}