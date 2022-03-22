package com.fancertification.www.data

import java.io.Serializable

data class ChannelData(
    var data: SearchData, var viewCount:Long=0, var subscriberCount:Int=0, var videoCount: Int =0
): Serializable

