package com.example.cinematv_mvvm.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.databinding.FragmentSearchBinding
import com.example.cinematv_mvvm.ui.film.adapter.FilmAdapter
import com.example.cinematv_mvvm.ui.film.listener.OnMovieClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment: Fragment(), OnMovieClickListener {
    private val TAG = this::class.java.simpleName
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var popularMovieAdapter: FilmAdapter
    private lateinit var topRatedMovieAdapter: FilmAdapter
    private lateinit var upcomingMovieAdapter: FilmAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setObservers()
    }

    private fun initView() {

        //Set upcoming films
        upcomingMovieAdapter = FilmAdapter(requireContext(), this)
        binding.upcomingRecycler.adapter = upcomingMovieAdapter
        binding.upcomingRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        //Set top rated films
        topRatedMovieAdapter = FilmAdapter(requireContext(), this)
        binding.topRatedRecycler.adapter = topRatedMovieAdapter
        binding.topRatedRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        //Set popular films
        popularMovieAdapter = FilmAdapter(requireContext(), this)
        binding.mostPopularRecycler.adapter = popularMovieAdapter
        binding.mostPopularRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        //Function to set search query
        setEditTextQuery()

        viewModel.getPopularMovies()
        viewModel.getTopRatedMovies()
        viewModel.getUpcomingMovies()
    }

    private fun setEditTextQuery() {
        binding.editQuery.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.i(TAG, "setEditTextQuery: ${binding.editQuery.text}")
                val action = SearchFragmentDirections.toSearchQueryResults()
                action.searchQuery = binding.editQuery.text.toString()
                findNavController().navigate(action)
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun setObservers() {
        viewModel.run {
            popularMovies.observe(viewLifecycleOwner){
                popularMovieAdapter.setData(it)
                binding.loadingText.visibility = View.INVISIBLE
            }
            topRatedMovies.observe(viewLifecycleOwner){
                topRatedMovieAdapter.setData(it)
                binding.loadingText1.visibility = View.INVISIBLE
            }
            upcomingMovies.observe(viewLifecycleOwner){
                upcomingMovieAdapter.setData(it)
                binding.loadingText2.visibility = View.INVISIBLE
            }
        }
    }

    override fun onMovieClick(filmId: Int) {
        val action = SearchFragmentDirections.toFilmDetails()
        action.filmID = filmId
        findNavController().navigate(action)
    }

}