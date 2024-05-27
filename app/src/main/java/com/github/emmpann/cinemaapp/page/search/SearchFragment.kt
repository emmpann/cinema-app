package com.github.emmpann.cinemaapp.page.search

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
import com.github.emmpann.cinemaapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchAdapter()

        binding.rvMovie.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovie.adapter = adapter

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
            }
        }

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsItem) {
                val toDetailFragment =
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment()
                toDetailFragment.movieId = data.id
                findNavController().navigate(toDetailFragment)
            }
        })

        with (binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionid, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    searchView.text.toString().let {
                        SearchHistoryManager.saveSearchQuery(requireContext(), it)
                    }
                    viewModel.setQuery(searchView.text.toString())
                    false
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}