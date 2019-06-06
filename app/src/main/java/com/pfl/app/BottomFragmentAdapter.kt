package com.pfl.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class BottomFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return BottomTabFragment.newInstance()
    }

    override fun getCount(): Int {
        return 3
    }
}
