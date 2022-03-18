package com.fancertification.www
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
import com.fancertification.www.databinding.BookmarkAdapterItemBinding
import java.text.SimpleDateFormat
import java.util.*


class BookmarkAdapter(var context: Context, private val list: ArrayList<ChannelData>?, date: ArrayList<String>) :
    RecyclerView.Adapter<BookmarkAdapter.BookMarkViewHolder>() {

    var itemOnClickListener:OnItemClickListener?=null

    var mList: ArrayList<ChannelData>?
    var dates: ArrayList<String>
    val df = SimpleDateFormat("yyyy-MM-dd") // 날짜 계산을 위한 Date 라이브러리
    val boldspan = StyleSpan(Typeface.BOLD) // 텍스트 변환을 위한 볼드 객체
    val textSize = RelativeSizeSpan(3.3f) // 텍스트 사이즈 변환을 위한 사이즈 객체 변환결과 3.3배 차이남
    val viewtextSize = RelativeSizeSpan(1.4f) // 총조회수용 텍스트 사이즈
    // TODO 동적으로 숫자의 크기에 따라 크기가 변화하거나 단위를 22.3억 이런식으로 남기는등의 표현 방식이 필요함
    
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

//            val viewtext = mList!![position].viewCount
//            val viewinfo = SpannableStringBuilder("총 영상 조회수\n$viewtext\n회")
//            val viewcount = ForegroundColorSpan(Color.parseColor("#8F57FF"))
//
//            viewinfo.setSpan(
//                viewcount,
//                9,
//                9 + viewtext.toString().length,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            viewinfo.setSpan(
//                boldspan,
//                9,
//                9 + viewtext.toString().length,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            viewinfo.setSpan(
//                viewtextSize,
//                9,
//                9 + viewtext.toString().length,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            viewholder.viewcount.setText(viewinfo)

            viewholder.viewcount.setText(mList!![position].viewCount.toString())


            /*viewholder.toggleButton.setOnClickListener {
                Log.d("view", it.toString())
                val show =
                    toggleLayout(!mList!![position].data.is_scraped, it, viewholder.layoutExpand)
                mList!![position].data.is_scraped = show

            }
*/
        }
    }

     fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
        // 토글 레이아웃을 위한 애니메이션
        toggleAnimation.toggleArrow(view, isExpanded)
         Log.d("hi", isExpanded.toString())
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
        dates = date

    }
}

