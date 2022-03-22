package com.fancertification.www
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fancertification.www.bookmark.BookmarkFragment
import com.fancertification.www.search.SearchFragment
import com.fancertification.www.setting.SettingFragment

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