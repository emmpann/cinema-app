package com.github.emmpann.cinemaapp.page.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.emmpann.cinemaapp.core.remote.response.ResultApi
import com.github.emmpann.cinemaapp.core.remote.response.ResultsItem
import com.github.emmpann.cinemaapp.databinding.FragmentUpcomingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingBinding
    private val viewModel: UpcomingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = UpcomingAdapter()

        binding.rvMovie.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovie.adapter = adapter

        viewModel.upcomingMovies.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultApi.Success -> {
                    adapter.submitList(result.data)
                    showLoading(false)
                }

                is ResultApi.Loading -> {
                    showLoading(true)
                }

                is ResultApi.Error -> {
                    showLoading(false)
                }
            }
        }

        adapter.setOnItemClickCallback(object : UpcomingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsItem) {
                val toDetailFragment =
                    UpcomingFragmentDirections.actionUpcomingFragmentToDetailFragment()
                toDetailFragment.movieId = data.id
                findNavController().navigate(toDetailFragment)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}