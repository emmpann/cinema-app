package com.github.emmpann.cinemaapp.page.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.emmpann.cinemaapp.R
import com.github.emmpann.cinemaapp.core.remote.response.ResultApi
import com.github.emmpann.cinemaapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = DetailFragmentArgs.fromBundle(arguments as Bundle).movieId
        viewModel.setMovieId(movieId)
        viewModel.movieDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultApi.Success -> {
                    viewModel.setCurrentMovie(result.data)
                    showLoading(false)
                    binding.movieTitle.text = result.data.title
                    binding.movieDesc.text = result.data.overview
                    binding.rating.text = String.format("%.1f", result.data.voteAverage)
                    Glide
                        .with(binding.root)
                        .load("https://image.tmdb.org/t/p/w185${result.data.posterPath}")
                        .into(binding.moviePoster)
                }

                is ResultApi.Loading -> {
                    showLoading(true)
                }

                is ResultApi.Error -> {

                }
            }
        }

        viewModel.getFavoriteMovie()

        viewModel.favoriteMovie.observe(viewLifecycleOwner) { item ->
            if (item != null) {
                binding.favButton.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.favButton.setImageResource(R.drawable.ic_favorite_border)
            }

            binding.favButton.setOnClickListener {
                if (item == null) {
                    viewModel.addMovieFavorite()
                } else {
                    viewModel.removeMovieFavorite()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}