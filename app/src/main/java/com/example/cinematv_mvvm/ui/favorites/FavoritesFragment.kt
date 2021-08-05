package com.example.cinematv_mvvm.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.databinding.FragmentFavoritesBinding
import com.example.cinematv_mvvm.ui.searchResult.adapter.SearchMovieResultAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment: Fragment(), SearchMovieResultAdapter.OnMovieClickListener  {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchMovieResultAdapter: SearchMovieResultAdapter
    private val viewModel: FavoritesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_favorites, container, false
        )
        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
        searchMovieResultAdapter = SearchMovieResultAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObservers()

        viewModel.getFavoriteMovies()
    }

    private fun initView() {
        binding.moviesRecycler.adapter = searchMovieResultAdapter
    }

    private fun setObservers() {
        viewModel.favoriteMovies.observe(viewLifecycleOwner){
            searchMovieResultAdapter.setData(it)
        }
    }

    override fun onMovieClick(filmId: Int) {
        val action = FavoritesFragmentDirections.toFilmDetails()
        action.filmID = filmId
        findNavController().navigate(action)
    }


}