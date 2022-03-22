package com.fancertification.www.bookmark
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fancertification.www.data.ChannelData
import com.fancertification.www.localdb.DBhelper
import com.fancertification.www.databinding.BookmarkAdapterItemBinding
import java.text.SimpleDateFormat
import java.util.*


class BookmarkAdapter(var context: Context, private val list: ArrayList<ChannelData>?, date: ArrayList<String>) :
    RecyclerView.Adapter<BookmarkAdapter.BookMarkViewHolder>() {

    var itemOnClickListener: OnItemClickListener?=null

    var mList: ArrayList<ChannelData>?
    var dates: ArrayList<String>
    val df = SimpleDateFormat("yyyy-MM-dd") // 날짜 계산을 위한 Date 라이브러리
    val boldspan = StyleSpan(Typeface.BOLD) // 텍스트 변환을 위한 볼드 객체
    val textSize = RelativeSizeSpan(3.3f) // 텍스트 사이즈 변환을 위한 사이즈 객체 변환결과 3.3배 차이남
    val viewtextSize = RelativeSizeSpan(1.4f) // 총조회수용 텍스트 사이즈
    
    interface OnItemClickListener {
        fun OnItemClick(holder: BookMarkViewHolder, view: View, data: ChannelData, position: Int)
    }

    inner class BookMarkViewHolder(binding: BookmarkAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val title: TextView = binding.titleTv //binding TextView in item_health_info.xml
        val detail: TextView = binding.subscriptionTv // binding TextView in item_health_info.xml
        val image: ImageView = binding.titleImage
        val viewcount: TextView = binding.viewcountContent
        val subdate: TextView = binding.subdateText
        val toggleButton:ImageView = binding.bookmarkBtn
        val layoutExpand: LinearLayout = binding.layoutExpand
        init {
            binding.bookmarkBtn?.setOnClickListener {
                itemOnClickListener?.OnItemClick(this, it, list!![adapterPosition],adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BookMarkViewHolder {
        return BookMarkViewHolder(BookmarkAdapterItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewholder: BookMarkViewHolder, position: Int) {
        
        if(mList!![position].data.videoId == "null"){ // db에 아무것도 없는 초기상황일경우
            viewholder.title.setText(mList!![position].data.title)
            viewholder.toggleButton.visibility = View.GONE
        }else {
            //영상제목 세팅
            viewholder.title.setText(mList!![position].data.title)
            //날짜 세팅
            viewholder.detail.setText(
                "총 동영상 개수 : " + mList!![position].videoCount.toString() + " · " +
                        ("구독자 : " + mList!![position].subscriberCount.toString())
            )
            //이미지를 넣어주기 위해 이미지url을 가져온다.
            val imageUrl: String = mList!![position].data.imageUrl
            //영상 썸네일 세팅
            Glide.with(viewholder.image)
                .load(imageUrl).circleCrop()
                .into(viewholder.image)

            // db에 기록한 날짜와 현재 날짜 차이를 구하는 과정
            val dbday = df.parse(dates!![position])
            val subday = ((Date().time - dbday.time) / 1000 / (24 * 60 * 60))

            //SpannableStringBuilder 를 통한 텍스트 변경
            val subinfo = SpannableStringBuilder("구독한 기간!\n$subday\n일째")
            val subcount = ForegroundColorSpan(Color.parseColor("#FF5454"))

            subinfo.setSpan(subcount,
                8,
                8 + subday.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            subinfo.setSpan(
                boldspan,
                8,
                8 + subday.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            subinfo.setSpan(
                textSize,
                8,
                8 + subday.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            viewholder.subdate.setText(subinfo)
        }
        viewholder.viewcount.setText(mList!![position].viewCount.toString()) // 총 조회수 표시
    }

     fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
        // 토글 레이아웃을 위한 애니메이션
         ToggleAnimation.toggleArrow(view, isExpanded)
         Log.d("hi", isExpanded.toString())
        if (isExpanded) {
            ToggleAnimation.expand(layoutExpand)
        } else {
            ToggleAnimation.collapse(layoutExpand)
        }
        return isExpanded
    }



    override fun getItemCount(): Int {
        return if (null != mList) mList!!.size else 0
    }

    init {
        mList = list
        dates = date

    }
}

