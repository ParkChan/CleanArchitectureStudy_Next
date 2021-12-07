package com.chan.movie.ui.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.chan.movie.BR
import com.chan.movie.R
import com.chan.movie.databinding.FragmentSaveListBinding
import com.chan.movie.domain.data.ItemData
import com.chan.movie.ui.main.MovieSearchViewModel
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
        binding.rvContent.adapter = BaseAdapter<ItemData>(
            layoutResourceId = R.layout.rv_save_item,
            viewHolderBindingId = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel)
        )
    }

    companion object {
        fun newInstance(): SaveListFragment = SaveListFragment()
    }
}