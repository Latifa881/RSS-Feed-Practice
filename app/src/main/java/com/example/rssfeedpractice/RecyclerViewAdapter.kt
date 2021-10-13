package com.example.rssfeedpractice

import android.app.AlertDialog
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter ( val details: ArrayList<Details>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = details[position]

        holder.itemView.apply {
            tvTitle.text = data.title
            tvTitle.setOnClickListener{
                val builder = AlertDialog.Builder(context)
                //set title for alert dialog
                builder.setTitle("More Details")
                //set message for alert dialog
               var summary= Html.fromHtml(Html.fromHtml(data.summary).toString())
                builder.setMessage("Title:${data.title}\n\n\n" +
                                    "Author:${data.author}\n\n\n"+
                                     "Rank:${data.rank}\n\n\n" +
                                    "Published:${data.published}\n\n\n"+
                                     "Updated:${data.updated}\n\n\n"+
                                    "summary:$summary\n\n\n")
                builder.setIcon(android.R.drawable.ic_dialog_alert)


                //performing negative action
                builder.setNegativeButton("Close"){dialogInterface, which ->
                    dialogInterface.cancel()
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
    }

    override fun getItemCount()=details.size
}