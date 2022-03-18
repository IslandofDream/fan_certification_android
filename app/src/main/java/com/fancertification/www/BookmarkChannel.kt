package com.fancertification.www

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private fun ChannelSearchTask(myData: ChannelData) {

        CoroutineScope(Dispatchers.IO).launch {

            // var myList: MutableList<Int>? = null
            var myList: MutableList<Long>? = null

            val getData = async {
                UtubeRepository.getChannel(myData.data.videoId)
            }
            myList = getData.await()
            val update = async{dBhelper.updateChannel(
                ChannelData(
                    myData.data,
                    myList?.get(0) ?: 0,
                    (myList?.get(1) ?: 0).toInt(),
                    (myList?.get(2) ?: 0).toInt()
                )
            )}
            Log.d("getData", myList.toString())
            withContext(Dispatchers.Main) {
                update.await()
                list_refresh()
            }
        }
    }

    private fun initData() {
        dBhelper = DBhelper(context)
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
                Log.d("DATA!!!", view.toString())
                channelData[position].data.is_scraped = bookmarkAdapter.toggleLayout(
                    data.data.is_scraped,
                    holder.toggleButton,
                    holder.layoutExpand
                )
                ChannelSearchTask(data)

            }

        }
        binding.list.adapter = bookmarkAdapter
    }

    fun list_refresh() {
        binding.apply {
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
                    Log.d("DATA!!!", view.toString())

                    channelData[position].data.is_scraped = bookmarkAdapter.toggleLayout(
                        data.data.is_scraped,
                        holder.toggleButton,
                        holder.layoutExpand
                    )
                    ChannelSearchTask(data)

                }

            }
            binding.list.adapter = bookmarkAdapter
        }
    }


}