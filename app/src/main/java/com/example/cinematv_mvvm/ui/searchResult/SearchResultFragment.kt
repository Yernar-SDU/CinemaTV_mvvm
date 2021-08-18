package com.example.cinematv_mvvm.ui.searchResult

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.databinding.FragmentSearchResultBinding
import com.example.cinematv_mvvm.ui.search.SearchFragmentDirections
import com.example.cinematv_mvvm.ui.searchResult.adapter.SearchMovieResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment :
    Fragment(), SearchMovieResultAdapter.OnMovieClickListener {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchResAdapter: SearchMovieResultAdapter
    private val args: SearchResultFragmentArgs by navArgs()
    private val viewModel: SearchResultViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_search_result, container, false
        )
        binding.viewModel = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObservers()

        viewModel.getMovies(true, args.searchQuery)
    }

    private fun setObservers() {
        viewModel.movies.observe(viewLifecycleOwner){
            searchResAdapter.setData(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        searchResAdapter = SearchMovieResultAdapter(this)
        binding.searchResultFilms.adapter = searchResAdapter
        binding.searchResultFilms.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchQueryText.text = "Search: ${args.searchQuery}"

        binding.backButton.setOnClickListener{
            activity?.onBackPressed()
        }

        initOnScrollReachesEnd()
    }

    private fun initOnScrollReachesEnd() {
        binding.searchResultFilms.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.adapter!!.itemCount != 0) {
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!
                            .itemCount - 1){
                        viewModel.getMovies(false, args.searchQuery)
                    }
                }
            }
        })
    }

    override fun onMovieClick(filmId: Int) {
        val action = SearchResultFragmentDirections.toFilmDetailed(filmId)
        findNavController().navigate(action)
    }
}