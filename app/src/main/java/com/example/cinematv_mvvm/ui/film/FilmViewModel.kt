package com.example.cinematv_mvvm.ui.film

import androidx.lifecycle.MutableLiveData
import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.cloud.ResultWrapper
import com.example.cinematv_mvvm.data.cloud.repository.BaseCloudRepository
import com.example.cinematv_mvvm.model.Movie
import com.example.cinematv_mvvm.utils.KEYS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FilmViewModel @Inject constructor(private val baseCloudRepository: BaseCloudRepository) :
    BaseViewModel() {

    val actionFilms = MutableLiveData(ArrayList<Movie>())
    val animationFilms = MutableLiveData(ArrayList<Movie>())
    val comedyFilms = MutableLiveData(ArrayList<Movie>())
    val adventureFilms = MutableLiveData(ArrayList<Movie>())
    val dramaFilms = MutableLiveData(ArrayList<Movie>())

    val isRefreshing: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }

    fun initializeAllMovies(position: Int) {
        launchIO {
            val genreName: String? = KEYS.TAB_GENRES[position]
            var dynamicFilms = MutableLiveData(ArrayList<Movie>())
            when(genreName){
                "Action" -> { dynamicFilms = actionFilms}
                "Animation" -> { dynamicFilms = animationFilms}
                "Comedy" -> { dynamicFilms = comedyFilms}
                "Adventure" -> { dynamicFilms = adventureFilms}
                "Drama" -> { dynamicFilms = dramaFilms}
            }


            isRefreshing.postValue(true)
            var page: Int = KEYS.FILM_REQUEST_PAGES[genreName]!!
            var count = 0
            while (count < 13){
                val wrapper = baseCloudRepository.getTrendingMovies(page)
                page++
                when (wrapper) {
                    is ResultWrapper.Success -> {
                        for (film in wrapper.value.results) {
                            if (KEYS.GENRES[film.genre_ids[0]].equals(genreName)) {
                                dynamicFilms.value?.add(film)

                                when(genreName){
                                    "Action" ->{
                                        actionFilms.postValue(dynamicFilms.value)
                                    }
                                    "Animation" ->{
                                        animationFilms.postValue(dynamicFilms.value)
                                    }
                                    "Comedy" -> {
                                        comedyFilms.postValue(dynamicFilms.value)
                                    }
                                    "Adventure" -> {
                                        adventureFilms.postValue(dynamicFilms.value)
                                    }
                                    "Drama" -> {
                                        dramaFilms.postValue(dynamicFilms.value)
                                    }
                                }


                                count++
                            }
                        }
                    }
                    is ResultWrapper.Error -> {}
                }
            }
            KEYS.FILM_REQUEST_PAGES[genreName!!] = page
            isRefreshing.postValue(false)
        }
    }


}