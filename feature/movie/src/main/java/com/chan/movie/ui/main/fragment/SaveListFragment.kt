package com.chan.movie.ui.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chan.movie.BR
import com.chan.movie.R
import com.chan.movie.databinding.FragmentSaveListBinding
import com.chan.movie.domain.data.Item
import com.chan.movie.ui.main.MovieSearchViewModel
import com.chan.ui.adapter.BaseAdapter

class SaveListFragment : Fragment(R.layout.fragment_save_list) {

    private val binding by viewBinding(FragmentSaveListBinding::bind)
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
        binding.rvContent.adapter = BaseAdapter<Item>(
            layoutResourceId = R.layout.rv_save_item,
            viewHolderBindingId = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel)
        )
    }

    companion object {
        fun newInstance(): SaveListFragment = SaveListFragment()
    }
}