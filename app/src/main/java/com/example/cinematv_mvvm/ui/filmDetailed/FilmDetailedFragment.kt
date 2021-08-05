package com.example.cinematv_mvvm.ui.filmDetailed

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.core.Config
import com.example.cinematv_mvvm.databinding.FragmentMovieExtendedBinding
import com.example.cinematv_mvvm.model.Movie
import com.example.cinematv_mvvm.model.MovieCredit
import com.example.cinematv_mvvm.model.VideoTrailer
import com.example.cinematv_mvvm.ui.filmDetailed.adapter.ActorRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilmDetailedFragment: Fragment(), ActorRecyclerAdapter.ActorClickListener {
    private val TAG = this::class.java.simpleName
    private var _binding: FragmentMovieExtendedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmDetailedViewModel by viewModels()
    private val args: FilmDetailedFragmentArgs by navArgs()
    private val actorRecyclerAdapter = ActorRecyclerAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_movie_extended, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.actorsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.actorsRecycler.adapter = actorRecyclerAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filmId = args.filmID.toString().toInt()

        viewModel.getMovieData(filmId)
        viewModel.getCreditsData(filmId)
        viewModel.getVideoData(filmId)

        initView()
        setObservers()
    }

    private fun initView() {
    }



    private fun setObservers() {
        viewModel.movie.observe(viewLifecycleOwner){
            setMovieUI(it)
        }
        viewModel.credits.observe(viewLifecycleOwner){
            setCreditUI(it)
        }
        viewModel.actors.observe(viewLifecycleOwner){
               actorRecyclerAdapter.setData(it)
        }
        viewModel.videoTrailer.observe(viewLifecycleOwner){
            setWatchClick(it)
        }
        viewModel.isOnWatchLaterList.observe(viewLifecycleOwner){
            Log.i(TAG, "isMovieHaveOnWatchLaterList: $it")
            setWatchLaterButtonUI(it)
            setOnWatchLaterButtonClick(it)
        }
    }


    private fun setWatchClick(videoTrailer: VideoTrailer) {
        binding.buttonWatch.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            if (videoTrailer.results.isEmpty()) {
                Toast.makeText(
                    this.context,
                    "The trailer doesn't provided for this video",
                    Toast.LENGTH_LONG
                ).show()

            }else{
                val videoKey = videoTrailer.results[0].key
                intent.data = Uri.parse(Config.BASE_YOUTUBE_VIDEO_URL + videoKey)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCreditUI(credit: MovieCredit) {
        if (credit.cast.isNotEmpty()){
            binding.chr2.text = "Actors: ${credit.cast[0].name}"
        }else{
            binding.chr2.text = "No actors"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieUI(movie: Movie) {
        binding.originalTitle.text = movie.title
        Glide.with(requireContext())
            .load(Config.IMAGE_URL + movie.backdrop_path)
            .centerCrop()
            .into(binding.movieImage)
        if (movie.genres.isNotEmpty()){
            binding.chr1.text = movie.release_date + " " + movie.genres[0].name
        }else{
            binding.chr1.text = movie.release_date +" Unknown genres"
        }
        binding.chr3.text = "time: ${movie.runtime}min rating: ${movie.vote_average}"
        binding.overview.text = movie.overview


    }

    override fun onActorClicked(actor_id: Int) {
        Log.i(TAG, "onActorClicked: $actor_id")
        val action = FilmDetailedFragmentDirections.toActorDetailed()
        action.actorId = actor_id
        findNavController().navigate(action)
    }


    private fun setWatchLaterButtonUI(isOnWatchLaterList: Boolean) {
        if (isOnWatchLaterList){
            binding.starImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.watchLaterText.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
        }else{
            binding.starImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.goodGrey))
            binding.watchLaterText.setTextColor(ContextCompat.getColor(requireContext(), R.color.goodGrey))
        }
    }

    private fun setOnWatchLaterButtonClick(isOnWatchLaterList: Boolean) {
        binding.saveToFavoritesButton.setOnClickListener{
            Log.i(TAG, "setOnWatchLaterButtonClick: $isOnWatchLaterList")
            if (viewModel.movie.value != null){
                if (isOnWatchLaterList){
                    viewModel.removeFromWatchLater(viewModel.movie.value!!)
                }else{
                    viewModel.addToWatchLater(viewModel.movie.value!!)
                }
            }
        }
    }
}