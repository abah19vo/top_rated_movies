package com.example.sogeti.mywatchlist.models

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class MovieList(

    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<Movie> = arrayListOf(),
    @SerializedName("total_results") var totalResults: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null

)