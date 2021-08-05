package com.example.cinematv_mvvm.ui.actorDetailed

import android.os.Bundle
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
import com.bumptech.glide.Glide
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.core.Config
import com.example.cinematv_mvvm.databinding.FragmentActorDetailedBinding
import com.example.cinematv_mvvm.model.Person
import com.example.cinematv_mvvm.ui.film.adapter.FilmAdapter
import com.example.cinematv_mvvm.ui.film.listener.OnMovieClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailedFragment: Fragment(), OnMovieClickListener {
    private var _binding: FragmentActorDetailedBinding? = null
    private val binding get() = _binding!!
    private val args: ActorDetailedFragmentArgs by navArgs()
    private val viewModel: ActorDetailedViewModel by viewModels()
    private  val TAG = this::class.java.simpleName
    private lateinit var filmAdapter: FilmAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_actor_detailed
            , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getPersonData(args.actorId)
        viewModel.getPersonMovies(args.actorId)

        initView()
        setObservers()
    }

    private fun initView() {
        filmAdapter = FilmAdapter(requireContext(), this)
        binding.actorMoviesRecycler.adapter = filmAdapter
        binding.actorMoviesRecycler.layoutManager =
            GridLayoutManager(requireContext(),3, LinearLayoutManager.VERTICAL, false)


        binding.backButton.setOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun setObservers() {
        viewModel.person.observe(viewLifecycleOwner){
            setPersonUI(it)
        }
        viewModel.personMovies.observe(viewLifecycleOwner){
            filmAdapter.setData(it)
        }
    }

    private fun setPersonUI(person: Person) {
        Glide.with(requireContext())
            .load(Config.IMAGE_URL + person.profile_path)
            .centerCrop()
            .into(binding.actorImage)
        binding.actorName.text = person.name
        binding.actorJob.text = person.job
    }

    override fun onMovieClick(filmId: Int) {
        val action = ActorDetailedFragmentDirections.toFilmDetails()
        action.filmID = filmId
        findNavController().navigate(action)
    }


}