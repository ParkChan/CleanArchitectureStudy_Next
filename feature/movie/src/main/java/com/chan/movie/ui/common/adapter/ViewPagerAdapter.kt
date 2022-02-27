package com.chan.movie.ui.common.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chan.movie.ui.main.fragment.SaveListFragment
import com.chan.movie.ui.main.fragment.SearchListFragment

internal class ViewPagerAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            SearchListFragment.newInstance()
        } else {
            SaveListFragment.newInstance()
        }
    }

    companion object {
        private const val FRAGMENT_COUNT = 2
    }

}