package com.fancertification.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

import android.os.AsyncTask

import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager

import android.R
import android.R.attr

import android.R.attr.button
import android.view.View
import org.json.JSONException

import android.R.attr.button
import org.json.JSONObject

import android.util.Log
import com.fancertification.www.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


 class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

     val sdata: ArrayList<SearchData> = ArrayList<SearchData>()
    val serverKey = "AIzaSyAMK7BBcUlJ81DjvkGL3mmPAZCcJeSjzRo"

    //  UtubeAdapter utubeAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.searchBtn.setOnClickListener {
                searchTask().execute();
        }

        setContentView(binding.root)
    }

    inner class searchTask :
        AsyncTask<Void?, Void?, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            try {
                //paringJsonData(getUtube())
                getUtube()
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                Log.d("myJsonError",e.toString())
                e.printStackTrace()
            } catch (e: IOException) {
                Log.d("myIOError",e.toString())
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {/*
            utubeAdapter = UtubeAdapter(this@MainActivity, sdata)
            recyclerview.setAdapter(utubeAdapter)
            utubeAdapter.notifyDataSetChanged()*/
            Log.d("result", result.toString())
        }

    }

    @Throws(IOException::class)
    fun getUtube(): JSONObject? {
        val originUrl = ("https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q=" + "blackpink"
                + "&key=" + serverKey + "&maxResults=50")
        val myUrl = String.format(originUrl)
        val url = URL(myUrl)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.setRequestMethod("GET")
        connection.setReadTimeout(10000)
        connection.setConnectTimeout(15000)
        connection.connect()
        var line: String?
        var result = ""
        val inputStream: InputStream = connection.getInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuffer()
        Log.d("inputStream!", reader?.toString())
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        println("검색결과$response")
        result = response.toString()
        var jsonObject = JSONObject()
        try {
            jsonObject = JSONObject(result)
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        Log.d("response", jsonObject.toString())
        return jsonObject
    }
/*
    @Throws(JSONException::class)
    private fun paringJsonData(jsonObject: JSONObject) {
        //재검색할때 데이터들이 쌓이는걸 방지하기 위해 리스트를 초기화 시켜준다.
        // sdata.clear();
        val contacts = jsonObject.getJSONArray("items")
        for (i in 0 until contacts.length()) {
            val c = contacts.getJSONObject(i)
            val kind = c.getJSONObject("id").getString("kind") // 종류를 체크하여 playlist도 저장
            vodid = if (kind == "youtube#video") {
                c.getJSONObject("id").getString("videoId") // 유튜브
                // 동영상
                // 아이디
                // 값입니다.
                // 재생시
                // 필요합니다.
            } else {
                c.getJSONObject("id").getString("playlistId") // 유튜브
            }
            val title = c.getJSONObject("snippet").getString("title") //유튜브 제목을 받아옵니다
            val changString: String = stringToHtmlSign(title)
            val date = c.getJSONObject("snippet").getString("publishedAt") //등록날짜
                .substring(0, 10)
            val imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                .getJSONObject("default").getString("url") //썸네일 이미지 URL값

            //JSON으로 파싱한 정보들을 객체화 시켜서 리스트에 담아준다.
            sdata.add(SearchData(vodid, changString, imgUrl, date))
        }
    }*/

    //var vodid = ""

//    //영상 제목을 받아올때 " ' 문자가 그대로 출력되기 때문에 다른 문자로 대체 해주기 위해 사용하는 메서드
//    private String stringToHtmlSign(String str) {
//        return str.replaceAll("&", "[&]")
//            .replaceAll("[<]", "<")
//            .replaceAll("[>]", ">")
//            .replaceAll(""", "'")
//                .replaceAll("'", "'");
//    }




}