package com.fancertification.www

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.ID
import android.provider.UserDictionary.Words.WORD
import android.util.Log
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull

class DBhelper(val context: Context?): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "mydatabase.db" //데베 이름
        val DB_VERSION = 1 // 버전정보
        val TABLE_NAME = "BookMarks" //테이블 이름
        val CHANNELID = "channelid" // 채널 아이디
        val CHANNELTITLE = "channeltitle" // 채널 제목
        val THUMBNAIL = "thumbnail"// 테이블 컬럼들
        val DESCRIPTION = "description"// 테이블 컬럼들
        val CHECKED = "checked"// 북마크
    }

    fun getCount(): Int { // 테이블의 총 튜플수 반환
        val strsql = "select * from $TABLE_NAME;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        return cursor.count
    }


    fun getALLRecord(): ArrayList<ExampleData> { // 모든 데이터 반환 북마크 프래그먼트에 출력하기 위함
        var data: ArrayList<ExampleData> = ArrayList()
        val strsql = "select * from $TABLE_NAME order by LOWER($CHANNELID) ASC;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        do {
            if(cursor.count != 0){
                data.add(
                    ExampleData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                    )
                )
            }
            else{
                data.add(
                    ExampleData(
                        "","원하는 채널을 저장해보세요.","","",0
                    )
                )
            }
        } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return data
    }


    fun checkoverbookmark(id: String): Boolean { // 검색 프래그먼트에서 검색결과를 띄울때 북마크 확인
        val strsql = "select * from $TABLE_NAME where $CHANNELID ='$id';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        return cursor.count == 0
    }

    fun insertchannel(data: ExampleData): Boolean { // 채널 삽입
        val values = ContentValues()
        values.put(CHANNELID, data.channelId)
        values.put(CHANNELTITLE, data.channeltitle)
        values.put(THUMBNAIL, data.imageUrl)
        values.put(DESCRIPTION, data.description)
        values.put(CHECKED, data.checked)
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values) > 0
        db.close()
        return flag
    }


    fun insertchanneltest(channelid :String, channeltitle: String, imageUrl: String, description: String): Boolean { // 채널 삽입
        val values = ContentValues()
        values.put(CHANNELID, channelid)
        values.put(CHANNELTITLE, channeltitle)
        values.put(THUMBNAIL, imageUrl)
        values.put(DESCRIPTION, description)
        values.put(CHECKED, 1)
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values) > 0
        db.close()
        return flag
    }


    fun deleteChannel(id: String): Boolean { // 채널 삭제 함수
        val strsql = "select * from $TABLE_NAME where $CHANNELID = '$id';"
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


    override fun onCreate(db: SQLiteDatabase?) { // 데이터베이스 처음 생성될때
        val create_table = "create table if not exists $TABLE_NAME(" +
                "$CHANNELID TEXT PRIMARY KEY, " +
                "$CHANNELTITLE TEXT, " +
                "$THUMBNAIL TEXT, " +
                "$DESCRIPTION TEXT, " +
                "$CHECKED INTEGER);"

        db!!.execSQL(create_table)
        // 테이블이 존재하지 않으면 생성 할거다
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) { // 데이터베이스 버전 바뀔때
        val drop_tabe = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_tabe)
        onCreate(db)
    }
}