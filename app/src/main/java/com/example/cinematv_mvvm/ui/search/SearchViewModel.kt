package com.example.cinematv_mvvm.ui.search

import androidx.lifecycle.MutableLiveData
import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.cloud.ResultWrapper
import com.example.cinematv_mvvm.data.cloud.repository.BaseCloudRepository
import com.example.cinematv_mvvm.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(val baseCloudRepository: BaseCloudRepository) : BaseViewModel() {

    val popularMovies = MutableLiveData(ArrayList<Movie>())
    val topRatedMovies = MutableLiveData(ArrayList<Movie>())
    val upcomingMovies = MutableLiveData(ArrayList<Movie>())


    fun getPopularMovies(){
        launchIO {
            when(val wrapper = baseCloudRepository.getPopularMoviesByPage(1)){
                is ResultWrapper.Success -> {
                    popularMovies.postValue(wrapper.value.results)
                }
                is ResultWrapper.Error -> {}
            }
        }
    }

    fun getTopRatedMovies(){
        launchIO {
            when(val wrapper = baseCloudRepository.getTopRatedMoviesByPage(1)){
                is ResultWrapper.Success -> {
                    topRatedMovies.postValue(wrapper.value.results)
                }
                is ResultWrapper.Error -> {}
            }
        }
    }

    fun getUpcomingMovies(){
        launchIO {
            when(val wrapper = baseCloudRepository.getUpcomingMoviesByPage(1)){
                is ResultWrapper.Success -> {
                    upcomingMovies.postValue(wrapper.value.results)
                }
                is ResultWrapper.Error -> {}
            }
        }
    }

}