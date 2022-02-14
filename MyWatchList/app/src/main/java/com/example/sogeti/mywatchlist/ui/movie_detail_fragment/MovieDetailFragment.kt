package com.example.sogeti.mywatchlist.ui.movie_detail_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sogeti.mywatchlist.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private var _binding: FragmentMovieDetailBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragment?.let {
            viewModel.movie.observe(it.viewLifecycleOwner) {
                _binding?.apply {
                    if (it != null) {
                        avgContent.text = it.voteAverage.toString()
                        genresContent.text = it.genres.map { it.name }.joinToString(", ")
                        title.text = it.title
                        status.text = it.status
                        overview.text = it.overview
                        releasedayContent.text = it.releaseDate
                        "${it.runtime} min".also { runtimeContent.text = it }
                        rRatedTitle.text = when (it.adult) {
                            true -> "Classification: 18+"
                            false -> null
                            else -> null
                        }
                        votesContent.text = it.voteCount.toString()
                        popularetyContent.text = it.popularity.toString()
                        taglineContent.text = it.tagline
                        spokenLangsContent.text =
                            it.spokenLanguages.map { it.name }.joinToString(", ")
                        val originalLangIso = it.originalLanguage
                        val originalLang = it.spokenLanguages.find {
                            it.iso6391 == originalLangIso
                        }
                        if (originalLang != null) {
                            orignalLangContent.text = originalLang.name

                        } else {
                            orignalLangTitle.text = null
                        }

                        movieDetailScrollView.visibility = View.VISIBLE
                    } else {
                        movieDetailScrollView.visibility = View.GONE
                    }

                    _binding?.toolbarLayout?.let { imageView ->
                        context?.let { ctx ->
                            viewModel.getMovieBackdrop(
                                imageView,
                                ctx
                            )
                        }
                    }

                }
            }
        }


    }


    override fun onStop() {
        parentFragment?.let { viewModel.movie.removeObservers(it.viewLifecycleOwner) }
        super.onStop()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }
}