package com.chan.movie.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.chan.movie.R
import com.chan.movie.databinding.ActivityMainBinding
import com.chan.movie.ui.common.adapter.ViewPagerAdapter
import com.chan.movie.ui.main.data.ClickEventMessage.*
import com.chan.ui.BaseActivity
import com.chan.ui.livedata.observeEvent
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val offscreenPageLimit = 1
    private val pagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this)
    }

    private val viewModel: MovieSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
        initViewModelObserve()
    }

    private fun initViewPager() {
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = offscreenPageLimit
        val tabLayout = binding.tabs
        val tabTitleList =
            listOf(
                getString(R.string.tab_search_list),
                getString(R.string.tab_save_list)
            )
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()
    }

    private fun initViewModelObserve() {
        viewModel.message.observeEvent(lifecycleOwner = this, observer = {
            it?.let {
                val resource = when (it) {
                    SAVE_SUCCESS -> R.string.save_success
                    DELETE_SUCCESS -> R.string.delete_success
                    ALREADY_EXIST -> R.string.already_exist
                }
                Snackbar.make(binding.root, resource, Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}