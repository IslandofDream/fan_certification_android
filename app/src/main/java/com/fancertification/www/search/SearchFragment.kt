package com.fancertification.www.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.fancertification.www.R
import com.fancertification.www.data.SearchData
import com.fancertification.www.data.ChannelData
import com.fancertification.www.databinding.SearchFragmentBinding
import com.fancertification.www.localdb.DBhelper
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class SearchFragment : Fragment() {

    lateinit var binding: SearchFragmentBinding
    var searchData: ArrayList<SearchData> = ArrayList()
    lateinit var utubeAdapter: UtubeAdapter
    lateinit var dBhelper: DBhelper
    var imm: InputMethodManager? = null


    @SuppressLint("NotifyDataSetChanged")
    override fun onPause() {
        super.onPause()
        searchData.clear()
        binding.searchEdit.text.clear()
        hideKeyboard(binding.searchEdit)
        utubeAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        imm =
            context!!.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        showKeyboard(binding.searchEdit)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dBhelper = DBhelper(context)
        val decoration = DividerItemDecoration(context, OrientationHelper.VERTICAL)
        decoration.setDrawable(context!!.resources!!.getDrawable(R.drawable.recyclerview_divider))
        // 구분선을 위한 데코레이션 객체 선언
        binding = SearchFragmentBinding.inflate(inflater, container, false)

        binding.searchEdit.setOnEditorActionListener { v, actionId, event ->

            Log.d("action", "enter")
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //searchTask().execute()
                SearchTask()
                hideKeyboard(binding.searchEdit)
            }
            true
        }

        utubeAdapter = UtubeAdapter(requireContext(), searchData)
        utubeAdapter.itemOnClickListener = object : UtubeAdapter.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun OnItemClick(
                holder: RecyclerView.ViewHolder,
                view: View,
                data: SearchData,
                position: Int
            ) {
                if (searchData[position].is_scraped) { // 이미 북마크 되어있는 경우 db에서 삭제하고 북마크 이미지를 변경합니다.
                    searchData[position].is_scraped = !searchData[position].is_scraped
                    utubeAdapter.notifyDataSetChanged()
                    dBhelper.deleteChannel(searchData[position].videoId)
                } else { // 북마크 되어있지 않은 경우 북마크 이미지를 변경하고 api를 호출해 데이터베이스에 넣어줍니다.
                    searchData[position].is_scraped = !searchData[position].is_scraped
                    utubeAdapter.notifyDataSetChanged()
                    ChannelSearchTask(data)
                }
            }
        }
        binding.recyclerView.addItemDecoration(decoration)
        binding.recyclerView.adapter = utubeAdapter
        return binding.root
    }

    fun showKeyboard(view: View) {
        binding.searchEdit.clearFocus()
        binding.searchEdit.requestFocus()
        GlobalScope.launch {
            delay(100)
            imm?.showSoftInput(binding.searchEdit, InputMethodManager.SHOW_IMPLICIT)

        }
    }

    fun hideKeyboard(v: View) {
        if (v != null) {
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("restore!!", "restore!!")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun SearchTask() {
        CoroutineScope(Dispatchers.IO).launch {
            UtubeRepository.getUtube(binding.searchEdit.text.toString())?.let {

                withContext(Main) {

                    searchData.clear()
                    val addData = async { searchData.addAll(it) }
                    utubeAdapter.notifyDataSetChanged()
                    addData.await()
                    onScrapedCheck(dBhelper.getALLRecord())
                    utubeAdapter.notifyDataSetChanged()
                }

            }
        }
    }

    private fun ChannelSearchTask(myData: SearchData) {

        CoroutineScope(Dispatchers.IO).launch {

            // var myList: MutableList<Int>? = null
            var myList: MutableList<Long>? = null

            val getData = async {
                UtubeRepository.getChannel(myData.videoId)
            }
            myList = getData.await()
            dBhelper.insertchannel(
                ChannelData(
                    myData,
                    myList?.get(0) ?: 0,
                    (myList?.get(1) ?: 0).toInt(),
                    (myList?.get(2) ?: 0).toInt()
                )
            )
        }
    }


    fun onScrapedCheck(myData: ArrayList<ChannelData>?) {
        searchData.forEach {
            val ramda = { d: ChannelData -> d.data.videoId == it.videoId }
            if (myData?.any(ramda) == true) {
                it.is_scraped = true
            }
        }
    }
/*

    @SuppressLint("StaticFieldLeak")
    inner class ChannelTask(private val myData: SearchData) :
        AsyncTask<Void?, Void?, Void?>() {
        var myList: MutableList<Int>? = null

        override fun doInBackground(vararg params: Void?): Void? {
            try {
                //paringJsonData(getUtube())
                UtubeRepository.getChannel(myData.videoId).let { myList = it }
                dBhelper.insertchannel(
                    ChannelData(
                        myData,
                        myList?.get(0) ?: 0,
                        myList?.get(1) ?: 0,
                        myList?.get(2) ?: 0
                    )
                )

            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                Log.d("myJsonError", e.toString())
                e.printStackTrace()
            } catch (e: IOException) {
                Log.d("myIOError", e.toString())
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            if (myList.isNullOrEmpty()) {
            }
        }
    }
*/


}