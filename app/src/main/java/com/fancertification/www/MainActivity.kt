package com.fancertification.www
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import org.json.JSONException
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.fancertification.www.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var mBackWait:Long = 0

val iconArray = arrayListOf<Int>(
    R.drawable.ic_star,
    R.drawable.ic_search,
    R.drawable.ic_setting
)
    //  UtubeAdapter utubeAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initdb()
        init()

        setContentView(binding.root)
    }
    private fun init() {
        binding.viewPager.adapter = FragmentHandler(this)
        initIconColor()
        TabLayoutMediator(binding.myTabIconview, binding.viewPager) { tab, position ->
            tab.setIcon(iconArray[position])
        }.attach() //꼭 attach해야함.
    }
    fun initIconColor() {
        binding.myTabIconview.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var tabiconColor = ContextCompat.getColor(applicationContext, R.color.selectRed)
                tab?.icon?.setColorFilter(tabiconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var tabiconColor =
                    ContextCompat.getColor(applicationContext, R.color.lightgray2)
                tab?.icon?.setColorFilter(tabiconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                var tabiconColor = ContextCompat.getColor(applicationContext, R.color.selectRed)
                tab?.icon?.setColorFilter(tabiconColor, PorterDuff.Mode.SRC_IN)
            }

        })
    }

    private fun initdb() {
        val dbfile = getDatabasePath("mydatabase.db") // 데이터베이스 있는지 알아본다.
        if (dbfile.parentFile.exists()) { // 존재한다면 디렉토리 만들어준다.
            dbfile.parentFile.mkdir()
        }
        if(!dbfile.exists()){ // 존재하지 않으면 새롭게 파일을 만들어준다.
            val file = resources.openRawResource(R.raw.mydatabase)
            val fileSize = file.available()
            val buffer = ByteArray(fileSize)
            file.read(buffer)
            file.close()
            dbfile.createNewFile()
            val output = FileOutputStream(dbfile)
            output.write(buffer)
            output.close()
        }

    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this,"'뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish() //액티비티 종료
        }
    }


}