package com.example.rssfeedpractice

import android.app.AlertDialog
import android.app.Dialog
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialogue_view.view.*
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.tvTitle
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


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
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialogue_view, null)
                builder.setView(dialogView)
                dialogView.tvTitle.text=data.title
                dialogView.tvAuthor.text=data.author.toString().capitalize()
                dialogView.tvRank.text=data.rank.toString()
                dialogView.tvPublish.text=data.published
                dialogView.tvUpdate.text=data.updated
                dialogView.tvSummary.text=Html.fromHtml(Html.fromHtml(data.summary).toString())
               dialogView.tvSummary.visibility=View.GONE
                dialogView.scrollable.visibility=View.GONE
                dialogView.ivSummary.setOnClickListener {
                    if(dialogView.tvSummary.visibility==View.VISIBLE){
                        dialogView.tvSummary.visibility=View.GONE
                        dialogView.scrollable.visibility=View.GONE
                    }else{
                        dialogView.tvSummary.visibility=View.VISIBLE
                        dialogView.scrollable.visibility=View.VISIBLE
                    }
                }
                try {
                    dialogView.ivLink.setOnClickListener {
                        var  uri = Uri.parse(data.link)
                       val intent =  Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent)
                    }
                }catch (e:Exception){

                }


                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
                dialogView.ivClose.setOnClickListener{
                    alertDialog.dismiss()
                }
            }
        }
    }

    override fun getItemCount()=details.size
}