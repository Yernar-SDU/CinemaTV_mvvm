package com.example.cinematv_mvvm.ui.film

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.databinding.FragmentFilmBinding
import com.example.cinematv_mvvm.ui.film.adapter.FilmAdapter
import com.example.cinematv_mvvm.ui.home.HomeFragmentDirections
import com.example.cinematv_mvvm.ui.film.listener.OnMovieClickListener
import com.example.cinematv_mvvm.utils.KEYS
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilmFragment: Fragment(), OnMovieClickListener {
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private var position: Int = 0
    private val viewModel: FilmViewModel by viewModels()
    private lateinit var adapter: FilmAdapter
    private var firstTime = true
    companion object{
        fun newInstance(position: Int): FilmFragment{
            val args = Bundle()
            args.putInt(KEYS.POSITION, position)
            val fragment = FilmFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        position = requireArguments().getInt(KEYS.POSITION)
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_film, container, false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObservers()
    }


    private fun initView() {
        adapter = FilmAdapter(requireContext(),this)
        binding.filmRecycler.adapter = adapter
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.filmRecycler.layoutManager = gridLayoutManager

        binding.filmRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.adapter!!.itemCount != 0) {
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!
                            .itemCount - 1){
                        Log.i("niash", "onScrollStateChanged: last")
                        viewModel.initializeAllMovies(position)
                    }
                }
            }
        })

    }

    private fun setObservers() {
        if (firstTime){
            viewModel.initializeAllMovies(position)
            firstTime = false
        }
        when(position){
            0 -> {
                viewModel.actionFilms.observe(viewLifecycleOwner) {
                    Log.i("busya", "setObservers: ")
                    adapter.setData(it)
                }
            }
            1 -> {
                viewModel.animationFilms.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
            2 -> {
                viewModel.comedyFilms.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
            3 -> {
                viewModel.adventureFilms.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
            4 -> {
                viewModel.dramaFilms.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
        }


    }


    override fun onMovieClick(filmId: Int) {
        val action = HomeFragmentDirections.toFilmDetails()
        action.filmID = filmId
        findNavController().navigate(action)
    }


}