package com.fancertification.www.search

import android.util.Log
import com.fancertification.www.data.SearchData
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object UtubeRepository {

    private const val serverKey = "AIzaSyAMK7BBcUlJ81DjvkGL3mmPAZCcJeSjzRo"

    private val searchData: ArrayList<SearchData> = ArrayList()


    fun getChannel(id: String): MutableList<Long>? { //MutableList<Int>?

        val originUrl = ("https://www.googleapis.com/youtube/v3/channels?"
                + "part=id,statistics,brandingSettings" + "&id=$id"
                + "&key=" + serverKey)
        val myUrl = String.format(originUrl)
        val url = URL(myUrl)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.readTimeout = 10000
        connection.connectTimeout = 15000
        connection.connect()
        var line: String?
        var result = ""
        val inputStream: InputStream = connection.getInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuffer()
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        result = response.toString()
        var jsonObject = JSONObject()
        try {
            jsonObject = JSONObject(result)
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        Log.d("result!", jsonObject.toString())
        return parsingJsonChannel(jsonObject)
    }

    @Throws(IOException::class)
    fun getUtube(keyword: String): ArrayList<SearchData>? {
        val originUrl = ("https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q=" + keyword + "&type=channel"
                + "&key=" + serverKey + "&maxResults=20")
        val myUrl = String.format(originUrl)
        val url = URL(myUrl)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.readTimeout = 10000
        connection.connectTimeout = 15000
        connection.connect()
        var line: String?
        var result = ""
        val inputStream: InputStream = connection.getInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuffer()
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        result = response.toString()
        var jsonObject = JSONObject()
        try {
            jsonObject = JSONObject(result)
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        Log.d("response", jsonObject.toString())
        return parsingJsonSearch(jsonObject)
    }

    private fun parsingJsonChannel(jsonObject: JSONObject?): MutableList<Long>? {

        val contacts = jsonObject?.getJSONArray("items")
        val c = contacts!!.getJSONObject(0)


        Log.d("here!", c.toString())
        //val changString: String = stringToHtmlSign(title)
        val viewCount = c.getJSONObject("statistics").getLong("viewCount")
        val subscriberCount: Long =
            if (c.getJSONObject("statistics").getBoolean("hiddenSubscriberCount")) {
                -1
            } else {
                c.getJSONObject("statistics").getLong("subscriberCount")
            }


        val videoCount = c.getJSONObject("statistics").getLong("videoCount")
        val myList = mutableListOf(
            viewCount,
            subscriberCount,
            videoCount
        )
        //val brandingSettings = c.getJSONObject("brandingSettings").getJSONObject("image")
        Log.d("viewCount", viewCount.toString())
        Log.d("subscriberCount", subscriberCount.toString())
        Log.d("videoCount", videoCount.toString())
        //brandingSettings?.toString()?.let { Log.d("brandingSettings", it) }
        //JSON?????? ????????? ???????????? ????????? ????????? ???????????? ????????????.

        return myList
    }

//    private fun parsingJsonChannel(jsonObject: JSONObject?): MutableList<Int>? {
//
//        val contacts = jsonObject?.getJSONArray("items")
//        val c = contacts!!.getJSONObject(0)
//
//
//        //val changString: String = stringToHtmlSign(title)
//        val viewCount = c.getJSONObject("statistics").getInt("viewCount")
//        val subscriberCount = c.getJSONObject("statistics").getInt("subscriberCount")
//        val videoCount = c.getJSONObject("statistics").getInt("videoCount")
//        val myList = mutableListOf(
//            viewCount,
//            subscriberCount,
//            videoCount
//        )
//        //val brandingSettings = c.getJSONObject("brandingSettings").getJSONObject("image")
//        Log.d("viewCount", viewCount.toString())
//        Log.d("subscriberCount", subscriberCount.toString())
//        Log.d("videoCount", videoCount.toString())
//        //brandingSettings?.toString()?.let { Log.d("brandingSettings", it) }
//        //JSON?????? ????????? ???????????? ????????? ????????? ???????????? ????????????.
//
//        return myList
//    }


    @Throws(JSONException::class)
    private fun parsingJsonSearch(jsonObject: JSONObject?): ArrayList<SearchData>? {
        //??????????????? ??????????????? ???????????? ???????????? ?????? ???????????? ????????? ????????????.
        searchData.clear()
        var vodid = ""
        val contacts = jsonObject?.getJSONArray("items")
        for (i in 0 until contacts!!.length()) {
            val c = contacts.getJSONObject(i)
            val kind = c.getJSONObject("id").getString("kind") // ????????? ???????????? playlist??? ??????
            vodid = if (kind == "youtube#video") {
                c.getJSONObject("id").getString("videoId") // ?????????
                // ?????????
                // ?????????
                // ????????????.
                // ?????????
                // ???????????????.
            } else {
                c.getJSONObject("id").getString("channelId") // ?????????
            }
            val title = c.getJSONObject("snippet").getString("title") //????????? ????????? ???????????????
            val changString: String = stringToHtmlSign(title)
            val description = c.getJSONObject("snippet").getString("description") //????????????
            val imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                .getJSONObject("default").getString("url") //????????? ????????? URL???

            //JSON?????? ????????? ???????????? ????????? ????????? ???????????? ????????????.
            searchData.add(SearchData(vodid, changString, imgUrl, description, false))
        }
        return searchData
    }


    private fun stringToHtmlSign(str: String): String {
        return str.replace("&", "[&]")
            .replace("[<]", "<")
            .replace("[>]", ">")
            .replace("\"", "\'")
            .replace("'", "'")
    }

}