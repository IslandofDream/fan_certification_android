package com.fancertification.www

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.fancertification.www.databinding.SearchFragmentBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class SearchFragment : Fragment() {

    lateinit var binding: SearchFragmentBinding
    var searchData: ArrayList<SearchData> = ArrayList()
    lateinit var utubeAdapter: UtubeAdapter
    lateinit var dBhelper: DBhelper


    @SuppressLint("NotifyDataSetChanged")
    override fun onPause() {
        super.onPause()
        searchData.clear()
        binding.searchEdit.text.clear()
        utubeAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dBhelper = DBhelper(context)
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.searchEdit.setImeActionLabel("Done", KeyEvent.KEYCODE_ENTER)


        binding.searchEdit.setOnEditorActionListener { v, actionId, event ->

            Log.d("action", "enter")
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //searchTask().execute()
                SearchTask()
                handled = true

            }
            handled
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
                searchData[position].is_scraped = !searchData[position].is_scraped
                utubeAdapter.notifyDataSetChanged()
                ChannelSearchTask(data)
            }
        }
        binding.recyclerView.adapter = utubeAdapter

        return binding.root
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
    private fun ChannelSearchTask(myData: SearchData){

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