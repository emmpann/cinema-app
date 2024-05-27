package com.github.emmpann.cinemaapp.page.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.emmpann.cinemaapp.core.remote.response.DetailResponse
import com.github.emmpann.cinemaapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavoriteAdapter()

        viewModel.getAllFavoriteMovie()

        binding.rvMovie.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovie.adapter = adapter

        viewModel.favMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.fabShare.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND);
            sendIntent.type = "text/plain";
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Share favorite movies.");
            startActivity(sendIntent);
        }

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailResponse) {
                val toDetailFragment =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment()
                toDetailFragment.movieId = data.id
                findNavController().navigate(toDetailFragment)
            }
        })
    }
}