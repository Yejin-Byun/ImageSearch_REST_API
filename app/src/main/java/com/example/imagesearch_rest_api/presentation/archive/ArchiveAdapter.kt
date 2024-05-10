package com.example.imagesearch_rest_api.presentation.archive

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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

class ArchiveAdapter(private val context: Context,  var data: MutableList<SearchData>) :
    RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>() {
    private val items = mutableListOf<SearchDocument>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArchiveAdapter.ArchiveViewHolder {
        val view = ListGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view.btnBookmark.setImageResource(R.drawable.ic_bookmark_fill)
        return ArchiveViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ArchiveAdapter.ArchiveViewHolder, position: Int) {
        val item = data[position]
        val parsed = OffsetDateTime.parse(item.datetime)
        val parseDate = parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val parseTime = parsed.format(DateTimeFormatter.ofPattern("HH:mm"))

        Glide.with(context)
            .load(item.url)
            .into(holder.image)
        holder.title.text = item.site
        holder.date.text = parseDate
        holder.time.text = parseTime
        holder.bookmark.setOnClickListener {

        }

        holder.bookmark.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                data.removeAt(position)
                (context as MainActivity).removeItemList(item)
                Log.d("removeArchive", "Archive 삭제")

                item.isLike = false
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ArchiveViewHolder(binding: ListGridItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imgResult
        val title = binding.tvSitename
        val date = binding.tvDate
        val time = binding.tvTime
        val bookmark = binding.btnBookmark

        init {
            items.addAll(data.map {
                SearchDocument(it.url, it.site, it.datetime)
            })
        }
    }

}