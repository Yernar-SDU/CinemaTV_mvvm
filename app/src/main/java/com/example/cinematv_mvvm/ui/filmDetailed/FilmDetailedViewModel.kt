package com.example.cinematv_mvvm.ui.filmDetailed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.cloud.ResultWrapper
import com.example.cinematv_mvvm.data.cloud.repository.BaseCloudRepository
import com.example.cinematv_mvvm.data.prefs.Prefs
import com.example.cinematv_mvvm.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FilmDetailedViewModel @Inject
constructor(
    private val baseCloudRepository: BaseCloudRepository,
    private val prefs: Prefs,
    savedStateHandle: SavedStateHandle
    ) : BaseViewModel() {
    private val TAG = this::class.java.simpleName
    val id:Int? = savedStateHandle.get("id")
    val movie = MutableLiveData(Movie())
    val credits = MutableLiveData(MovieCredit())
    val actors = MutableLiveData(ArrayList<Person>())
    val videoTrailer = MutableLiveData(VideoTrailer())
    val isOnWatchLaterList = MutableLiveData(false)

    fun getMovieData(movie_id: Int) {
        launchIO {
            when (val wrapper = baseCloudRepository.getMovieById(movie_id)) {
                is ResultWrapper.Error -> {
                }
                is ResultWrapper.Success -> {
                    movie.postValue(wrapper.value)
                    isMovieHaveOnWatchLaterList(wrapper.value.id)
                }
            }
        }
    }

    fun getCreditsData(movie_id: Int) {
        launchIO {
            when (val wrapper =  baseCloudRepository.getCreditsById(movie_id)) {
                is ResultWrapper.Error -> {
                    Log.i(TAG, "getCreditsData: ${wrapper.error}")
                }
                is ResultWrapper.Success -> {
                    credits.postValue(wrapper.value)
                    actors.postValue(wrapper.value.cast)
                }
            }
        }
    }

    fun getVideoData(movie_id: Int) {
        launchIO {
            when (val wrapper =  baseCloudRepository.getMovieVideoById(movie_id)) {
                is ResultWrapper.Success -> {
                    videoTrailer.postValue(wrapper.value)
                }
                is ResultWrapper.Error -> {
                }
            }
        }
    }

    fun addToWatchLater(movie: Movie){
        launchIO {
            isOnWatchLaterList.postValue(true)
            prefs.addFavoriteMovie(movie)
            Log.i(TAG, "addToWatchLater: ${prefs.getMovies().size}")
        }
    }

    fun removeFromWatchLater(movie: Movie){
        launchIO {
            isOnWatchLaterList.postValue(false)
            prefs.deleteFavoriteMovie(movie)
            Log.i(TAG, "addToWatchLater: ${prefs.getMovies().size}")
        }
    }

    private fun isMovieHaveOnWatchLaterList(movie_id: Int){
        val watchLaterMovies = prefs.getMovies()
        for(movie in watchLaterMovies){
            if (movie.id == movie_id){
                isOnWatchLaterList.postValue(true)
                return
            }
        }
        isOnWatchLaterList.postValue(false)
    }


}