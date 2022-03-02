package com.fancertification.www

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fancertification.www.databinding.ActivityMainBinding
import com.fancertification.www.databinding.TestBinding
import java.io.FileOutputStream

class test : AppCompatActivity() {

    lateinit var binding: TestBinding
    lateinit var dBhelper: DBhelper
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = TestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dBhelper = DBhelper(this)
        initdb()
    }


    private fun initdb() {
        val dbfile = getDatabasePath("mydatabase.db") // 데이터베이스 있는지 알아본다.
        if (dbfile.parentFile.exists()) { // 존재한다면 디렉토리 만들어준다.
            dbfile.parentFile.mkdir()
        }
//        if(!dbfile.exists()){ // 존재하지 않으면 새롭게 파일을 만들어준다.
//            val file = resources.openRawResource(R.raw.mydatabase)
//            val fileSize = file.available()
//            val buffer = ByteArray(fileSize)
//            file.read(buffer)
//            file.close()
//            dbfile.createNewFile()
//            val output = FileOutputStream(dbfile)
//            output.write(buffer)
//            output.close()
//        }

    }
}