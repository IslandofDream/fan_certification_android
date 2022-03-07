package com.fancertification.www

import java.io.Serializable

data class ChannelData(
    var data:SearchData, var viewCount:Int=0, var subscriberCount:Int=0, var videoCount:Int=0
): Serializable

