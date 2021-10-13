package com.example.rssfeedpractice

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var rvMain: RecyclerView
    private lateinit var questionsDetails: ArrayList<Details>
    val searchArray = ArrayList<Details>()

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
            searchArray.addAll(questionsDetails)
            return questionsDetails
        }

        override fun onPostExecute(result: ArrayList<Details>?) {
            super.onPostExecute(result)
            Log.d("TAG", questionsDetails.toString())
            rvMain.adapter = RecyclerViewAdapter(searchArray)
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.adapter!!.notifyDataSetChanged()

        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu?.findItem(R.id.search_action)

        if (menuItem != null) {
            val searchItem = menuItem.actionView as SearchView
            searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        searchArray.clear()
                        val search = newText!!.toLowerCase(Locale.getDefault())
                        questionsDetails.forEach {
                            if (it.title?.toLowerCase(Locale.getDefault()).toString()
                                    .contains(search)
                            ) {
                                searchArray.add(it)
                            }
                        }
                        rvMain.adapter!!.notifyDataSetChanged()
                    } else {
                        searchArray.clear()
                        searchArray.addAll(questionsDetails)
                        rvMain.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.getItemId()==R.id.refresh){
            FetchQuestionsDetails().execute()
        }
        return super.onOptionsItemSelected(item)
    }

}