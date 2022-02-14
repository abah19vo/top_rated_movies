package com.example.sogeti.mywatchlist.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sogeti.mywatchlist.databinding.MovieListContentBinding
import com.example.sogeti.mywatchlist.models.Movie
import com.example.sogeti.mywatchlist.ui.movie_list_fragment.MovieListViewModel


class MovieRecyclerViewAdapter(
    private val values: List<Movie>,
    private val onClickListener: View.OnClickListener,
    private val context: Context,
    private val getMoviePoster: (image: ImageView, context: Context, backdropPath: String) -> Unit,
) :
    RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            MovieListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.overviewView.text = item.overview
        holder.rate.text = "${item.voteAverage} /10"
        holder.releaseDateView.text = item.releaseDate

        item.posterPath?.let { getMoviePoster(holder.moviePosterView, context, it) }
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }


    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: MovieListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.title
        val overviewView: TextView = binding.overview
        val rate: TextView = binding.rate
        val releaseDateView: TextView = binding.releaseDate
        val moviePosterView: ImageView = binding.moviePoster
    }

}

