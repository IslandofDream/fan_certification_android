package com.fancertification.www

import java.io.Serializable

data class ExampleData(
    var channelId: String, var channeltitle: String, var imageUrl: String,
    var description: String, var position: Int, var checked: Int
):Serializable
