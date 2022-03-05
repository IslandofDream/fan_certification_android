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

    }



}