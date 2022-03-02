package com.fancertification.www

import android.os.AsyncTask
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
import org.json.JSONException
import java.io.IOException

class SearchFragment : Fragment() {

    lateinit var binding: SearchFragmentBinding
    var sdata: ArrayList<SearchData> = ArrayList<SearchData>()
    lateinit var utubeAdapter: UtubeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.searchEdit.setImeActionLabel("Done", KeyEvent.KEYCODE_ENTER)


        binding.searchEdit.setOnEditorActionListener { v, actionId, event ->

            Log.d("action", "enter")
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTask().execute()
                handled = true

            }
            handled
        }

        utubeAdapter = UtubeAdapter(requireContext(), sdata)

        utubeAdapter.itemOnClickListener= object:UtubeAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: RecyclerView.ViewHolder,
                view: View,
                data: SearchData,
                position: Int
            ) {
                sdata[position].is_scraped = !sdata[position].is_scraped

                utubeAdapter.notifyDataSetChanged()
            }

        }
        binding.recyclerView.adapter = utubeAdapter

        return binding.root
    }
    inner class searchTask :
        AsyncTask<Void?, Void?, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            try {
                //paringJsonData(getUtube())
                UtubeRepository.getUtube(binding.searchEdit.text.toString())?.let {
                    sdata.clear()
                    sdata.addAll(it)
                }
                Log.d("result!!!", sdata.toString())
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


            utubeAdapter.notifyDataSetChanged()
        }

    }

}