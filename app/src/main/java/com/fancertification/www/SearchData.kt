package com.fancertification.www

import java.io.Serializable

//URL로 부터 가지고 온 데이터를 객체화 시켜서 리사이클러뷰에 넣어주기 위한 클래스입니다.

//URL로 부터 가지고 온 데이터를 객체화 시켜서 리사이클러뷰에 넣어주기 위한 클래스입니다.
data class SearchData(
    var videoId: String="", var title: String="", var imageUrl: String="",
    var description: String="", var is_scraped:Boolean=false
):Serializable

