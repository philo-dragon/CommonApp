package com.pfl.app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.pfl.common.bottom_tab.TabBean
import kotlinx.android.synthetic.main.activity_home.*

class BottomTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var tabs = mutableListOf<TabBean>()
        tabs.add(TabBean("首页","http://img3.imgtn.bdimg.com/it/u=4037180060,1570819241&fm=26&gp=0.jpg","http://img5.imgtn.bdimg.com/it/u=663168039,1004982392&fm=26&gp=0.jpg", Color.BLACK,Color.BLUE,R.drawable.ic_tab_libray_select,R.drawable.ic_tab_libray_normal))
        tabs.add(TabBean("阅读练习",null,null, Color.BLACK,Color.BLUE,R.drawable.ic_tab_read_practiice_select,R.drawable.ic_tab_read_practiice_normal))
        tabs.add(TabBean("我的",null,null, Color.BLACK,Color.BLUE,R.drawable.ic_tab_my_select,R.drawable.ic_tab_my_normal))
        tabLayout.setTabDate(tabs)
        tabLayout.setupWithViewPager(view_pager)
        view_pager.adapter = BottomFragmentAdapter(supportFragmentManager)
        view_pager.currentItem = 0
    }


}
