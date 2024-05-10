package com.example.imagesearch_rest_api.presentation.search

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch_rest_api.R
import com.example.imagesearch_rest_api.data.SearchData
import com.example.imagesearch_rest_api.databinding.ListGridItemBinding
import com.example.imagesearch_rest_api.presentation.MainActivity
import com.example.imagesearch_rest_api.retrofit.SearchDocument
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchAdapter(
    private val items: MutableList<SearchDocument>,
    private val context: Context,
    var data: MutableList<SearchData>
) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = ListGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(view.root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = items[position]
        val userData = data[position]
        val parsed = OffsetDateTime.parse(item.dateTime)
        val parseDate = parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val parseTime = parsed.format(DateTimeFormatter.ofPattern("HH:mm"))

        Glide.with(context)
            .load(item.thumbnailUrl)
            .into(holder.image)
        holder.title.text = item.siteName
        holder.date.text = parseDate
        holder.time.text = parseTime

        if(userData.isLike)
            holder.bookmark.setImageResource(R.drawable.ic_bookmark_fill)
        else
            holder.bookmark.setImageResource(R.drawable.ic_bookmark_empty)

    }

    inner class SearchViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.img_result)
        val title = view.findViewById<TextView>(R.id.tv_sitename)
        val date = view.findViewById<TextView>(R.id.tv_date)
        val time = view.findViewById<TextView>(R.id.tv_time)
        val bookmark = view.findViewById<ImageButton>(R.id.btn_bookmark)

        init {
            bookmark.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val userData = data[position]
                    if (userData.isLike) {
                        (context as MainActivity).removeItemList(userData)
                        Log.d("removeSearch", "Search 삭제")
                    } else {
                        (context as MainActivity).addItemList(userData)
                        Log.d("addSearch", "추가")
                    }
                    userData.isLike = !userData.isLike
                    notifyDataSetChanged()
                }

            }
        }

    }
}
