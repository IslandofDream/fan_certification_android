package com.fancertification.www

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fancertification.www.databinding.FragmentBookmarkBinding
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class BookmarkChannel : Fragment() {
    lateinit var binding: FragmentBookmarkBinding
    lateinit var dBhelper: DBhelper
    lateinit var bookmarkAdapter: BookmarkAdapter
    lateinit var channelData: ArrayList<ChannelData>
    lateinit var dates: ArrayList<String>
//    lateinit var position: ArrayList<Int> // 화면상의 포지션

    override fun onResume() {
        super.onResume()
        list_refresh()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        initData()
        binding.apply {
            list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        }
        return binding.root
    }

    private fun ChannelSearchTask(myData: ChannelData, is_scraped: Boolean, position: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            // var myList: MutableList<Int>? = null
            var myList: MutableList<Long>? = null

            val getData = async {
                UtubeRepository.getChannel(myData.data.videoId)
            }
            myList = getData.await()
            val update = async {
                dBhelper.updateChannel(
                    ChannelData(
                        SearchData(
                            myData.data.videoId,
                            myData.data.title,
                            myData.data.imageUrl,
                            myData.data.description,
                            is_scraped
                        ),
                        myList?.get(0) ?: 0,
                        (myList?.get(1) ?: 0).toInt(),
                        (myList?.get(2) ?: 0).toInt()
                    )
                )
            }
            Log.d("getData", myList.toString())
            withContext(Dispatchers.Main) {
                update.await()
                //list_refresh()
                channelData[position] = ChannelData(
                    SearchData(
                        myData.data.videoId,
                        myData.data.title,
                        myData.data.imageUrl,
                        myData.data.description,
                        is_scraped
                    ),
                    myList?.get(0) ?: 0,
                    (myList?.get(1) ?: 0).toInt(),
                    (myList?.get(2) ?: 0).toInt()
                )
            }
        }
    }

    private fun initData() {
        dBhelper = DBhelper(context)
        channelData = dBhelper.getALLRecord()
        dates = dBhelper.getAlldate()
        bookmarkAdapter = BookmarkAdapter(requireContext(), channelData, dates)

        binding.list.adapter = bookmarkAdapter
        bookmarkAdapter.itemOnClickListener = object : BookmarkAdapter.OnItemClickListener {
            override fun OnItemClick(
                holder: BookmarkAdapter.BookMarkViewHolder,
                view: View,
                data: ChannelData,
                position: Int
            ) {
                data.data.is_scraped = toggleLayout(
                    !data.data.is_scraped,
                    view,
                    holder.layoutExpand
                )
                ChannelSearchTask(data, data.data.is_scraped, position)
            }

        }
        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing=true
            list_refresh()
            binding.swiperefresh.isRefreshing=false

        }
    }


    fun list_refresh() {
        channelData = dBhelper.getALLRecord()
        dates = dBhelper.getAlldate()
        bookmarkAdapter = BookmarkAdapter(requireContext(), channelData, dates)
        bookmarkAdapter.itemOnClickListener = object : BookmarkAdapter.OnItemClickListener {
            override fun OnItemClick(
                holder: BookmarkAdapter.BookMarkViewHolder,
                view: View,
                data: ChannelData,
                position: Int
            ) {
                data.data.is_scraped = toggleLayout(
                    !data.data.is_scraped,
                    view,
                    holder.layoutExpand
                )
                ChannelSearchTask(data, data.data.is_scraped, position)
            }

        }
        binding.list.adapter = bookmarkAdapter
    }

    fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
        // 토글 레이아웃을 위한 애니메이션
        toggleAnimation.toggleArrow(view, isExpanded)
        if (isExpanded) {
            toggleAnimation.expand(layoutExpand)
        } else {
            Log.d("call!", "Call!!!")
            toggleAnimation.collapse(layoutExpand)
        }
        return isExpanded
    }

}