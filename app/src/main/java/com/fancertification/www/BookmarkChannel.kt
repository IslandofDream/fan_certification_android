package com.fancertification.www

import android.app.AlertDialog
import android.os.Bundle
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
    lateinit var data: ArrayList<ExampleData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager  = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        dBhelper = DBhelper(context)
        data = dBhelper.getALLRecord()
        recyclerView.adapter = BookmarkAdapter(data)
        (recyclerView.adapter as BookmarkAdapter).notifyDataSetChanged()
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
                    dBhelper.deleteChannel(data[viewHolder.adapterPosition].channeltitle)
                    (recyclerView.adapter as BookmarkAdapter).removeItem(viewHolder.adapterPosition) // 리싸이클러뷰 갱신
                    Toast.makeText(context,"채널 삭제 성공", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("아니오") { dialog, id -> dialog.dismiss()
                    Toast.makeText(context,"채널 삭제가 취소 되었습니다.", Toast.LENGTH_SHORT).show()
                    recyclerView.adapter = BookmarkAdapter(dBhelper.getALLRecord()) // 스와이프 사용 취소가 되었으므로 목록을 복구 시켜놔야 한다.
                    (recyclerView.adapter as BookmarkAdapter).refresh()
                }
                builder.create().show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        binding.insert.setOnClickListener{
            dBhelper.insertchanneltest("asdf","adsf","adsf","asdf")
        }
        return view
    }

}