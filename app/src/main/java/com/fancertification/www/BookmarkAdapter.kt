package com.fancertification.www
import android.content.Context
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.fancertification.www.databinding.BookmarkAdapterItemBinding
import com.fancertification.www.databinding.ExampleAdapterItemBinding
import com.google.api.Distribution
import kotlin.collections.ArrayList


class BookmarkAdapter(var context: Context, list: ArrayList<SearchData>?) :
    RecyclerView.Adapter<BookmarkAdapter.BookMarkViewHolder>() {


    var mList: ArrayList<SearchData>?

    var itemOnClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun OnItemClick(holder: RecyclerView.ViewHolder, view: View, data: SearchData, position: Int)
    }

    inner class BookMarkViewHolder(binding: BookmarkAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val title: TextView = binding.titleTv //binding TextView in item_health_info.xml
        val detail: TextView = binding.subscriptionTv // binding TextView in item_health_info.xml
        val image: ImageView = binding.titleImage
        val inner: TextView = binding.innerText
        val toggleButton:ImageView = binding.bookmarkBtn
        val layoutExpand: LinearLayout = binding.layoutExpand
        val mainitem: LinearLayout = binding.mainItem
        init {

        }
    }

    fun removeItem(pos:Int){ // 스와이프에 쓰일 아이
        mList?.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun moveItem(curpos : Int, targetpos : Int){
        notifyItemMoved(curpos,targetpos)
        val dbhelper = DBhelper(context)
        for(i in 0..mList!!.size)
        {
            //TODO 포지션 정보를 업데이트 갱신
        //dbhelper.upadatePosition(mList!![].position)
        }

    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BookMarkViewHolder {


        //item_utube xml파일을 객체화 시킨다.

        return BookMarkViewHolder(BookmarkAdapterItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewholder: BookMarkViewHolder, position: Int) {
        //영상제목 세팅
        viewholder.title.setText(mList!![position].title)
        //날짜 세팅
        viewholder.detail.setText(mList!![position].description)


        //이미지를 넣어주기 위해 이미지url을 가져온다.
        val imageUrl: String = mList!![position].imageUrl
        //영상 썸네일 세팅
        Glide.with(viewholder.image)
            .load(imageUrl).circleCrop()
            .into(viewholder.image)

        viewholder.inner.setText(mList!![position].title)

        viewholder.toggleButton.setOnClickListener { //click event for suggestion btton
            val show = toggleLayout(!mList!![position].is_scraped, it, viewholder.layoutExpand)
            mList!![position].is_scraped = show
        }


    }

    fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
        // 토글 레이아웃을 위한 애니메이션
        toggleAnimation.toggleArrow(view, isExpanded)
        if (isExpanded) {
            toggleAnimation.expand(layoutExpand)
        } else {
            toggleAnimation.collapse(layoutExpand)
        }
        return isExpanded
    }


    override fun getItemCount(): Int {
        return if (null != mList) mList!!.size else 0
    }

    init {
        mList = list
    }
}

