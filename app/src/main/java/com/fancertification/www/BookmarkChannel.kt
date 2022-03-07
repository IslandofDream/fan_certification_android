package com.fancertification.www

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fancertification.www.databinding.FragmentBookmarkBinding
import java.util.*
import kotlin.collections.ArrayList

class BookmarkChannel : Fragment() {
    lateinit var binding: FragmentBookmarkBinding
    lateinit var dBhelper: DBhelper
    lateinit var bookmarkAdapter: BookmarkAdapter
    lateinit var data: ArrayList<SearchData>
    lateinit var ids: ArrayList<String>
//    lateinit var position: ArrayList<Int> // 화면상의 포지션

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        binding.apply {
            list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            dBhelper = DBhelper(context)
            data = dBhelper.getALLRecord()
            //position = dBhelper.getPosition()
            bookmarkAdapter = BookmarkAdapter(requireContext(), data)
            //data = dBhelper.getALLRecord()
            list.adapter = bookmarkAdapter
            //bookmarkAdapter.notifyDataSetChanged()
            ids = dBhelper.getALLChannleid()
            Log.e("ids", ids.toString())
            val simpleCallBack = object : ItemTouchHelper.SimpleCallback( //스와이프 사용을 위함
                ItemTouchHelper.DOWN or ItemTouchHelper.UP, ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val curpos: Int = viewHolder.adapterPosition
                    val targetpos: Int = target.adapterPosition
                    bookmarkAdapter.moveItem(curpos, targetpos)
                    reorderposition(curpos, targetpos)
                    Log.e("order", data.toString())
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val builder = AlertDialog.Builder(context) // 채널 삭제전 dialog로 확인 절차
                    builder.setTitle("채널 삭제")
                    builder.setMessage("채널을 삭제하시겠습니까?")
                    builder.setPositiveButton("예") { dialog, id ->
                        dBhelper.deleteChannel(data[viewHolder.adapterPosition].title)

                        //bookmarkAdapter.removeItem(viewHolder.adapterPosition) // 리싸이클러뷰 갱신
                        data = dBhelper.getALLRecord() // 로컬 변수 갱신
               //         position = dBhelper.getPosition() //포지션 갱신
                        bookmarkAdapter = BookmarkAdapter(
                            requireContext(),
                            data
                        ) // 스와이프 사용으로 arraylist가 변경되었으므로 목록을 복구 시켜놔야 한다.
                        //TODO 포지션 정보 갱신 필요
                        binding.list.adapter = bookmarkAdapter
                        Toast.makeText(context, "채널 삭제 성공", Toast.LENGTH_SHORT).show()
                    }
                    builder.setNegativeButton("아니오") { dialog, id ->
                        dialog.dismiss()
                        Toast.makeText(context, "채널 삭제가 취소 되었습니다.", Toast.LENGTH_SHORT).show()
                        data = dBhelper.getALLRecord() // 로컬 변수 갱신
                        //position = dBhelper.getPosition() //포지션 갱신
                        bookmarkAdapter = BookmarkAdapter(
                            requireContext(),
                            data
                        ) // 스와이프 사용 취소가 되었으므로 목록을 복구 시켜놔야 한다.
                        binding.list.adapter = bookmarkAdapter
                        //TODO 포지션 정보 갱신 필요
                    }
                    builder.create().show()
                }
            }
            val itemTouchHelper = ItemTouchHelper(simpleCallBack)
            itemTouchHelper.attachToRecyclerView(list)
        }

        binding.insert.setOnClickListener {
            binding.insertChannel.text
//            position.add(position.size + 1)
//            Log.e("position", position.last().toString())
//            Log.e("list", position.toString())
            val smapleData = SearchData(
                binding.insertChannel.text.toString(),
                binding.insertChannel.text.toString(),
                "https://picsum.photos/id/237/200/300",
                "됐냐?",
                true
            )

            val flag = dBhelper.insertchannel(smapleData)
            if (flag) {
                Toast.makeText(it.context, "삽입 성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(it.context, "삽입 실패", Toast.LENGTH_SHORT).show()
            }
/*
            for (i in 1..position.size) { // 여기서 화면상의
                for (j in position) {
                    dBhelper.upadatePosition(i, data[j - 1].channelId)
                }
            }
*/
            data = dBhelper.getALLRecord() // 로컬 변수 갱신
//            position = dBhelper.getPosition() //포지션 갱신
            bookmarkAdapter = BookmarkAdapter(requireContext(), data)
            binding.list.adapter = bookmarkAdapter
            binding.insertChannel.text.clear()
            //TODO 포지션 정보 갱신 필요
            //bookmarkAdapter.notifyDataSetChanged()
        }
        return binding.root
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