package com.github.emmpann.cinemaapp.page.play

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
import com.github.emmpann.cinemaapp.databinding.FragmentPlayBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayFragment : Fragment() {

    private val viewModel: PlayViewModel by viewModels()
    private lateinit var binding: FragmentPlayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlayBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PlayAdapter()

        binding.rvMovie.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovie.adapter = adapter

        adapter.setOnItemClickCallback( object : PlayAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsItem) {
                val toDetailFragment = PlayFragmentDirections.actionPlayFragmentToDetailFragment()
                toDetailFragment.movieId = data.id
                findNavController().navigate(toDetailFragment)
            }
        })

        viewModel.movies.observe(viewLifecycleOwner) { result ->
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

                else -> {}
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}