package com.example.rssfeedpractice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var rvMain: RecyclerView
    private lateinit var questionsDetails: ArrayList<Details>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMain = findViewById(R.id.rvMain)
        FetchQuestionsDetails().execute()
    }

    private inner class FetchQuestionsDetails: AsyncTask<Void, Void, ArrayList<Details>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): ArrayList<Details> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            questionsDetails =
                urlConnection.getInputStream()?.let {
                    parser.parse(it)
                }
                        as ArrayList<Details>
            return questionsDetails
        }

        override fun onPostExecute(result: ArrayList<Details>?) {
            super.onPostExecute(result)
            Log.d("TAG", questionsDetails.toString())
            rvMain.adapter = RecyclerViewAdapter(questionsDetails)
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
          //  rvMain.adapter!!.notifyDataSetChanged()

        }

    }

}