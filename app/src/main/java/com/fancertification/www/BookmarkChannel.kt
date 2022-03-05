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

class BookmarkChannel: Fragment() {
    lateinit var binding: FragmentBookmarkBinding
    lateinit var dBhelper: DBhelper
    lateinit var bookmarkAdapter: BookmarkAdapter
    lateinit var data: ArrayList<ExampleData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        binding.apply {
        list.layoutManager  = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        dBhelper = DBhelper(context)
            data = dBhelper.getALLRecord()
            bookmarkAdapter = BookmarkAdapter(requireContext(), data)
//        data = dBhelper.getALLRecord()
            list.adapter = bookmarkAdapter
            bookmarkAdapter.notifyDataSetChanged()
        val simpleCallBack = object : ItemTouchHelper.SimpleCallback( //스와이프 사용을 위함
            ItemTouchHelper.DOWN or ItemTouchHelper.UP
            , ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val builder= AlertDialog.Builder(context) // 단어 삭제전 dialog로 확인 절차
                builder.setTitle("채널 삭제")
                builder.setMessage("채널을 삭제하시겠습니까?")
                builder.setPositiveButton("예") { dialog, id ->
                    dBhelper.deleteChannel(data[viewHolder.adapterPosition].channelId)
                    data.removeAt(viewHolder.adapterPosition)
                    //bookmarkAdapter.removeItem(viewHolder.adapterPosition) // 리싸이클러뷰 갱신
                    bookmarkAdapter = BookmarkAdapter(requireContext(), data) // 스와이프 사용으로 arraylist가 변경되었으므로 목록을 복구 시켜놔야 한다.
                    data = dBhelper.getALLRecord()
                    binding.list.adapter = bookmarkAdapter
                    Toast.makeText(context,"채널 삭제 성공", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("아니오") { dialog, id -> dialog.dismiss()
                    Toast.makeText(context,"채널 삭제가 취소 되었습니다.", Toast.LENGTH_SHORT).show()
                    bookmarkAdapter = BookmarkAdapter(requireContext(), data) // 스와이프 사용 취소가 되었으므로 목록을 복구 시켜놔야 한다.
                    binding.list.adapter = bookmarkAdapter
                }
                builder.create().show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(list)
        }

        binding.insert.setOnClickListener{
            val smapleData = ExampleData("1234","삽입 테스트","https://picsum.photos/id/237/200/300","됐냐?",0)
            val flag = dBhelper.insertchannel(smapleData)
            Log.d("asdf", flag.toString())
            if(flag){
                Toast.makeText(it.context, "삽입 성공", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(it.context, "삽입 실패", Toast.LENGTH_SHORT).show()
            }
            bookmarkAdapter = BookmarkAdapter(requireContext(), dBhelper.getALLRecord())
            binding.list.adapter = bookmarkAdapter
            bookmarkAdapter.notifyDataSetChanged()
        }


        return binding.root
    }

}