package com.fancertification.www
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentHandler(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {



    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {  //뷰페이저 페이지 전환
        return when (position) {
            0 -> BookmarkFragment()
            1 -> SearchFragment()
            2 -> SettingFragment()
            else -> SearchFragment()
        }
    }

}