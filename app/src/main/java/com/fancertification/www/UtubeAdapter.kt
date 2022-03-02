package com.fancertification.www
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import android.R
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.fancertification.www.databinding.ExampleAdapterItemBinding
import java.util.ArrayList


class UtubeAdapter(var context: Context, list: ArrayList<SearchData>?) :
    RecyclerView.Adapter<UtubeAdapter.UtubeViewHolder>() {
    var mList: ArrayList<SearchData>?

    var itemOnClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun OnItemClick(holder: RecyclerView.ViewHolder, view: View, data: SearchData, position: Int)
    }

    inner class UtubeViewHolder(binding: ExampleAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val title: TextView = binding.titleTv //binding TextView in item_health_info.xml
        val detail: TextView = binding.subscriptionTv // binding TextView in item_health_info.xml
        val image: ImageView = binding.titleImage

        init {
            binding.subscriptionBtn.setOnClickListener { //click event for suggestion btton
                itemOnClickListener?.OnItemClick(this, it,
                    mList?.get(adapterPosition)!!, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): UtubeViewHolder {


        //item_utube xml파일을 객체화 시킨다.

        return UtubeViewHolder(ExampleAdapterItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewholder: UtubeViewHolder, position: Int) {


        //영상제목 세팅
        viewholder.title.setText(mList!![position].title)
        //날짜 세팅
        viewholder.detail.setText(mList!![position].publishedAt)


        //이미지를 넣어주기 위해 이미지url을 가져온다.
        val imageUrl: String = mList!![position].imageUrl
        //영상 썸네일 세팅
        Glide.with(viewholder.image)
            .load(imageUrl)
            .into(viewholder.image)
    }

    override fun getItemCount(): Int {
        return if (null != mList) mList!!.size else 0
    }

    init {
        mList = list
    }
}

