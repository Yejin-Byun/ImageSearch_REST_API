package com.example.imagesearch_rest_api.data

data class SearchData(
    var url : String,
    var site : String,
    var datetime : String,
    var isLike : Boolean = false
)
