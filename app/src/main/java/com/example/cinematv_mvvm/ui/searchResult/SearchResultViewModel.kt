package com.example.cinematv_mvvm.ui.searchResult

import androidx.lifecycle.MutableLiveData
import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.cloud.ResultWrapper
import com.example.cinematv_mvvm.data.cloud.repository.BaseCloudRepository
import com.example.cinematv_mvvm.model.Movie
import com.example.cinematv_mvvm.utils.KEYS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchResultViewModel @Inject constructor(val baseCloudRepository: BaseCloudRepository): BaseViewModel(){
    val movies = MutableLiveData(ArrayList<Movie>())
    val moviess = ArrayList<Movie>()
    val isRefreshing: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }

    fun getMovies(isFromFirstPage: Boolean,query: String){
        isRefreshing.postValue(true)
        launchIO {
            var page = 1
            if (!isFromFirstPage){
                page = KEYS.FILM_REQUEST_PAGES["Search"]!!
            }else{
                KEYS.FILM_REQUEST_PAGES["Search"] = 1
            }

            when(val wrapper = baseCloudRepository.getMoviesSearch(page, query)){
                is ResultWrapper.Success ->{
                    moviess.addAll(wrapper.value.results)
                    movies.postValue(moviess)
                }
                is ResultWrapper.Error ->{}
            }
            page++
            KEYS.FILM_REQUEST_PAGES["Search"] = page
        }
        isRefreshing.postValue(false)
    }

}