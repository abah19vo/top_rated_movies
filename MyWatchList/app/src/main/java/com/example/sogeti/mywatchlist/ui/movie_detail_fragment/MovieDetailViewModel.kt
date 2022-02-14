package com.example.sogeti.mywatchlist.ui.movie_detail_fragment

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.sogeti.mywatchlist.lazysingelton.MovieListInstance
import com.example.sogeti.mywatchlist.models.MovieDetails
import com.example.sogeti.mywatchlist.services.ApiInstance
import com.example.sogeti.mywatchlist.services.ImageService
import com.example.sogeti.mywatchlist.services.ImageSizes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val apiInstance = ApiInstance
    private val movieListInstance = MovieListInstance


    val movie: MutableLiveData<MovieDetails> = MutableLiveData<MovieDetails>()

    init {
        val selectedMovie = movieListInstance.selectedMovie

        if (selectedMovie != null) {
            viewModelScope.launch(Dispatchers.Main) {
                getMovie(selectedMovie.id)
            }
        }
    }

    private suspend fun getMovie(id: Int) {
        val response = apiInstance.movieService.getMovieDetails(id)
        movie.postValue(response.body() as MovieDetails?)

    }

    fun getMovieBackdrop(image: ImageView, context: Context) {
        val backdropPath = movieListInstance.selectedMovie?.backdropPath
        val posterPath = movieListInstance.selectedMovie?.posterPath
        if (backdropPath != null) {

            ImageService.loadImage(
                backdropPath,
                ImageSizes.Large,
                context,
            ).into(image)

        }else if (posterPath != null){
            ImageService.loadImage(
                posterPath,
                ImageSizes.Large,
                context,
            ).into(image)
        }
    }
}