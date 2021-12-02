package com.chan.movie.ui.main

import android.annotation.SuppressLint
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
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val pagerAdapter: ViewPagerAdapter by lazy{
        ViewPagerAdapter(this)
    }
    private val viewModel by viewModels<MovieSearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
        initViewModelObserve()
    }

    @SuppressLint("WrongConstant")
    private fun initViewPager() {
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = VIEWPAGER_OFFSCREEN_PAGE_LIMIT
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

    companion object {
        const val VIEWPAGER_OFFSCREEN_PAGE_LIMIT = 1
    }

}