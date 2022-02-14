package com.example.sogeti.mywatchlist.ui.movie_list_fragment

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sogeti.mywatchlist.lazysingelton.MovieListInstance
import com.example.sogeti.mywatchlist.models.Movie
import com.example.sogeti.mywatchlist.models.MovieList
import com.example.sogeti.mywatchlist.services.ApiInstance
import com.example.sogeti.mywatchlist.services.ImageService
import com.example.sogeti.mywatchlist.services.ImageSizes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {

    private val apiInstance = ApiInstance
    private val movieListInstance = MovieListInstance
    var movieList = MutableLiveData<MovieList>()

    init {

        viewModelScope.launch(Dispatchers.Main) {
            getMovieList(1)
        }
    }

    fun setSelectedMovie(movie: Movie) {
        movieListInstance.selectedMovie = movie
    }


    private suspend fun getMovieList(page: Int) {
        try{
            val response = apiInstance.movieService.getMovieList(page)
            movieList.apply {
                this.value = (response.body() as MovieList)
                if (value != null) {
                    movieListInstance.movieList.page = value?.page
                    movieListInstance.movieList.results.addAll(value?.results!!)

                }
            }
        }catch(e: Exception){

        }

    }

    fun getMoviePoster(image: ImageView, context: Context, path: String) {
        ImageService.loadImage(
            path,
            ImageSizes.Small,
            context,
        ).into(image)
    }


}