package com.example.sogeti.mywatchlist.ui.movie_list_fragment


import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sogeti.mywatchlist.R
import com.example.sogeti.mywatchlist.adapter.MovieRecyclerViewAdapter
import com.example.sogeti.mywatchlist.databinding.FragmentMovieListBinding
import com.example.sogeti.mywatchlist.databinding.MovieListContentBinding
import com.example.sogeti.mywatchlist.models.Movie
import com.example.sogeti.mywatchlist.services.ImageService
import com.example.sogeti.mywatchlist.services.ImageSizes

class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModels()

    private var _binding: FragmentMovieListBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.movieList
        val movieDetailFragmentContainer: View? = view.findViewById(R.id.movie_detail_nav_container)
        val onClickListener = View.OnClickListener { movieView ->
            val movie = movieView.tag as Movie?
            if(movie != null){
                viewModel.setSelectedMovie(movie)
                val bundle = Bundle()
                if (movieDetailFragmentContainer != null) {
                    movieDetailFragmentContainer.findNavController()
                        .navigate(R.id.fragment_movie_detail, bundle)
                } else {
                    movieView.findNavController().navigate(R.id.show_movie_detail, bundle)
                }
            }

        }
        setupRecyclerView(recyclerView, onClickListener)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: View.OnClickListener,
    ) {
        viewModel.movieList.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerView.adapter = context?.let { cnx ->
                    MovieRecyclerViewAdapter(
                        it.results,
                        onClickListener,
                        cnx,
                       ::getMoviePoster
                    )
                }
            }

        }
    }

    fun getMoviePoster(image: ImageView, context: Context, path: String) {
        viewModel.getMoviePoster(image, context, path)
    }



    override fun onStop() {
        parentFragment?.let { viewModel.movieList.removeObservers(it.viewLifecycleOwner) }
        super.onStop()

    }

    override fun onDestroyView() {
        _binding = null
        parentFragment?.let { viewModel.movieList.removeObservers(it.viewLifecycleOwner) }
        super.onDestroyView()

    }
}