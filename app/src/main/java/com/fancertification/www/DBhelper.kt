package com.fancertification.www

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.UserDictionary.Words.WORD
import android.util.Log
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DBhelper(val context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "mydatabase.db" //데베 이름
        const val DB_VERSION = 1 // 버전정보
        const val TABLE_NAME = "BookMarks" //테이블 이름
        const val CHANNELID = "channelid" // 채널 아이디
        const val CHANNELTITLE = "channeltitle" // 채널 제목
        const val THUMBNAIL = "thumbnail"// 썸네일을 위한 이미지 URL
        const val DESCRIPTION = "description"// 채널 설명
        const val SUB_DATE = "sub_data" // 구독 날짜
        const val VIEW_COUNT = "view_count" // 총 조회수
        const val SUB_COUNT = "sub_count" // 구독자 수
        const val VIDEO_COUNT = "video_count" // 총 비디오 갯수
    }

    //채널 아이디, 제목, 썸네일, 요약, 포지션, 구독자수, 구독기간(로컬) 정도까지는 만들어놔야할듯  지금 현재 포지션, 구독자수, 구독기간 관련해서 애트리뷰트가 없는듯합니다. Checked 대신 포지션으로 ㄱㄱ
    @SuppressLint("Recycle")
    fun getCount(): Int { // 테이블의 총 튜플수 반환
        val strsql = """select * from $TABLE_NAME;"""
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        return cursor.count
    }


    fun getALLRecord(): ArrayList<ChannelData> { // 모든 데이터 반환 북마크 프래그먼트에 출력하기 위함
        val data: ArrayList<ChannelData> = ArrayList()
        val strsql = """select * from $TABLE_NAME;"""
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        do {
            if (cursor.count != 0) {
                data.add(
                    ChannelData(
                        SearchData(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        ),
                        cursor.getLong(4),
                        cursor.getInt(5),
                        cursor.getInt(6)
                    )
                )
            } else {
                data.add(
                    ChannelData(
                        SearchData("null", "          원하는 채널을 저장해보세요!", "", ""), 0, 0, 0
                    )
                )
            }
        } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return data
    }

    fun getAlldate(): ArrayList<String> { // 데이터의 아이디를 모두 반환
        val data: ArrayList<String> = ArrayList()
        val strsql = """select $SUB_DATE from $TABLE_NAME;"""
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        do {
            if (cursor.count != 0) {
                data.add(
                    cursor.getString(0)
                )
            } else {
                data.add("null")
            }
        } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return data
    }
    fun updateChannel(channeldata:ChannelData): Boolean{
        val db = writableDatabase  //변경시킬꺼니까
        //val cursor =db.rawQuery(strsql,null)
        val values = ContentValues()
        //values.put(CHANNELID, channeldata.data.videoId)
        values.put(CHANNELTITLE, channeldata.data.title)
        values.put(THUMBNAIL, channeldata.data.imageUrl)
        values.put(DESCRIPTION, channeldata.data.description)
        values.put(VIEW_COUNT, channeldata.viewCount)
        values.put(SUB_COUNT,  channeldata.subscriberCount)
        values.put(VIDEO_COUNT, channeldata.videoCount)
        val flag = db.update(TABLE_NAME, values, "$CHANNELID =?", arrayOf(channeldata.data.videoId))
        db.close()
       return flag == 1
//        cursor.close()
    }

//    fun getPosition(): ArrayList<Int> { // 모든 데이터 반환 북마크 프래그먼트에 출력하기 위함
//        var position: ArrayList<Int> = ArrayList()
//        val strsql = "select * from $TABLE_NAME order by LOWER($POSITION) ASC;"
//        val db = readableDatabase
//        val cursor = db.rawQuery(strsql, null)
//        cursor.moveToFirst()
//        do {
//            if(cursor.count != 0){ position.add(cursor.getInt(0)) }
//        } while (cursor.moveToNext())
//        cursor.close()
//        db.close()
//        return position
//    }


    @SuppressLint("Recycle")
    fun checkoverbookmark(id: String): Boolean { // 검색 프래그먼트에서 검색결과를 띄울때 북마크 확인
        val strsql = """select * from $TABLE_NAME where $CHANNELID ='$id';"""
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        return cursor.count == 0
    }

//    fun upadatePosition(pos:Int, id:String) :Boolean{ // 위치 변경을 위한 함수
//        val db = writableDatabase  //변경시킬꺼니까
//            Log.e("dbpos",pos.toString())
//            val strsql = "update $TABLE_NAME set $POSITION = '$pos' where $CHANNELID = '$id';"
//            val cursor = db.rawQuery(strsql,null)
//            if(cursor.count > 0) {
//                cursor.close()
//                db.close()
//                return true
//            }
//            else{
//                cursor.close()
//                db.close()
//                return false
//            }
//    }

    @SuppressLint("SimpleDateFormat")
    fun insertchannel(data: ChannelData): Boolean { // 채널 삽입
        val df = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val today = df.format(date)
        val values = ContentValues()
        values.put(CHANNELID, data.data.videoId)
        values.put(CHANNELTITLE, data.data.title)
        values.put(THUMBNAIL, data.data.imageUrl)
        values.put(DESCRIPTION, data.data.description)
        values.put(VIEW_COUNT, data.viewCount)
        values.put(SUB_COUNT, data.subscriberCount)
        values.put(VIDEO_COUNT, data.videoCount)
        values.put(SUB_DATE, today.toString())
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values) > 0
        db.close()
        return flag
    }


    fun deleteChannel(id: String): Boolean { // 채널 삭제 함수
        val strsql = """select * from $TABLE_NAME where $CHANNELID = '$id';"""
        val db = writableDatabase  //삭제할꺼니까
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count != 0
        if (flag) {
            cursor.moveToFirst()
            db.delete(TABLE_NAME, "$CHANNELID =?", arrayOf(id))
        }
        cursor.close()
        db.close()
        return flag
    }

    fun upDateBookmark(id: String, position: Int): String {
//https://jong99.tistory.com/114 참고
        return "Success"
    }

    override fun onCreate(db: SQLiteDatabase?) { // 데이터베이스 처음 생성될때

        val create_table = "create table if not exists $TABLE_NAME(" +
                "$CHANNELID TEXT PRIMARY KEY, " +
                "$CHANNELTITLE TEXT, " +
                "$THUMBNAIL TEXT, " +
                "$DESCRIPTION TEXT," +
                "$VIEW_COUNT INTEGER," +
                "$SUB_COUNT INTEGER," +
                "$VIDEO_COUNT INTEGER," +
                "$SUB_DATE DATE);"

        db!!.execSQL(create_table)
        // 테이블이 존재하지 않으면 생성 할거다
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) { // 데이터베이스 버전 바뀔때
        val drop_tabe = """drop table if exists $TABLE_NAME;"""
        db!!.execSQL(drop_tabe)
        onCreate(db)
    }
}