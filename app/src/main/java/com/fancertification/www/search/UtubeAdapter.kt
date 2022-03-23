package com.fancertification.www.search
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import android.R
import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.fancertification.www.data.SearchData
import com.fancertification.www.databinding.SearchAdapterItemBinding
import java.util.ArrayList


class UtubeAdapter(var context: Context, list: ArrayList<SearchData>?) :
    RecyclerView.Adapter<UtubeAdapter.UtubeViewHolder>() {
    var mList: ArrayList<SearchData>?

    var itemOnClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun OnItemClick(holder: RecyclerView.ViewHolder, view: View, data: SearchData, position: Int)
    }

    inner class UtubeViewHolder(binding: SearchAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val title: TextView = binding.titleTv //binding TextView in item_health_info.xml
        val detail: TextView = binding.subscriptionTv // binding TextView in item_health_info.xml
        val image: ImageView = binding.titleImage
        val bookmark: ImageButton = binding.bookmarkBtn

        init {
            binding.bookmarkBtn.setOnClickListener { //click event for suggestion button
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

        return UtubeViewHolder(SearchAdapterItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewholder: UtubeViewHolder, position: Int) {
        viewholder.title.setText(mList!![position].title)
        viewholder.detail.setText(mList!![position].description)
        if(mList!![position].is_scraped){
            viewholder.bookmark.setColorFilter(ContextCompat.getColor(context,R.color.holo_red_light) ,PorterDuff.Mode.SRC_IN)
        }else{
            viewholder.bookmark.setColorFilter(ContextCompat.getColor(context,R.color.darker_gray) ,PorterDuff.Mode.SRC_IN)
        }
        //이미지를 넣어주기 위해 이미지url을 가져온다.
        val imageUrl: String = mList!![position].imageUrl
        //영상 썸네일 세팅
        Glide.with(viewholder.image)
            .load(imageUrl).circleCrop()
            .into(viewholder.image)

    }

    override fun getItemCount(): Int {
        return if (null != mList) mList!!.size else 0
    }

    init {
        mList = list
    }
}

