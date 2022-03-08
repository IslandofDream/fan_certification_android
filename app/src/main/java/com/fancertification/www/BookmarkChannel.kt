package com.fancertification.www

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fancertification.www.databinding.FragmentBookmarkBinding
import kotlin.collections.ArrayList

class BookmarkChannel : Fragment() {
    lateinit var binding: FragmentBookmarkBinding
    lateinit var dBhelper: DBhelper
    lateinit var bookmarkAdapter: BookmarkAdapter
    lateinit var data: ArrayList<ChannelData>
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
        binding.apply {
            list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            dBhelper = DBhelper(context)
            list_refresh()
        }

        binding.insert.setOnClickListener {
//            position.add(position.size + 1)
//            Log.e("position", position.last().toString())
//            Log.e("list", position.toString())
            val sampleData = SearchData(
                binding.editchannel.text.toString(),
                binding.editchannel.text.toString(),
                "https://picsum.photos/id/237/200/300",
                "됐냐?",
                false
            )

            if (dBhelper.insertchannel(ChannelData(sampleData,0,0,0))) {
                Toast.makeText(context, "삽입 성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "삽입 실패", Toast.LENGTH_SHORT).show()
            }

            list_refresh()
            binding.editchannel.text.clear()

        }
        binding.Update.setOnClickListener {

            val smapleDate2 = SearchData(
                binding.editchannel.text.toString(),
                binding.editchannel.text.toString(),
                "https://placeimg.com/320/100/any",
                "업데이트?",
                false
            )
            if (dBhelper.updateChannel(ChannelData(smapleDate2,100,100,100))) {
                Toast.makeText(context, "업데이트 성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "업데이트 실패", Toast.LENGTH_SHORT).show()
            }

            list_refresh()
            binding.editchannel.text.clear()
        }
        return binding.root
    }

    fun list_refresh(){
        binding.apply {
            data = dBhelper.getALLRecord()
            dates = dBhelper.getAlldate()
            bookmarkAdapter = BookmarkAdapter(requireContext(), data, dates)
            list.adapter = bookmarkAdapter
        }
    }


    fun reorderposition(curpos: Int, targetpos: Int) { // 화면상에서 바뀌는 포지션 번호를 변환해주는 함수
//        if (curpos < targetpos) {
//            for (i in curpos until targetpos) {
//                Collections.swap(position, i, i + 1)
//            }
//        } else {
//            for (i in curpos downTo targetpos + 1) {
//                Collections.swap(position, i, i - 1)
//            }
//        }
    }

}