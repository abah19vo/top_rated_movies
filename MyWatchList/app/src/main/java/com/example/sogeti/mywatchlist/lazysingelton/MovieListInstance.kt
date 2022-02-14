package com.example.sogeti.mywatchlist.lazysingelton

import com.example.sogeti.mywatchlist.models.Movie
import com.example.sogeti.mywatchlist.models.MovieList

object MovieListInstance {
    var movieList : MovieList = MovieList()

    var selectedMovie : Movie? = null

}