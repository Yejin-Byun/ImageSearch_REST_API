package com.example.imagesearch_rest_api.retrofit

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SearchDTO(val response: ImageResponse)

data class ImageResponse(
    @SerializedName("meta")
    val meta: SearchMeta,
    @SerializedName("documents")
    val documents: MutableList<SearchDocument>
)

data class SearchDocument(
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("display_sitename")
    val siteName: String,
    @SerializedName("datetime")
    val dateTime: String
)

data class SearchMeta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)