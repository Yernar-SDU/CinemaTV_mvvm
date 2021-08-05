package com.example.cinematv_mvvm.ui.actorDetailed

import androidx.lifecycle.MutableLiveData
import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.cloud.ResultWrapper
import com.example.cinematv_mvvm.data.cloud.repository.BaseCloudRepository
import com.example.cinematv_mvvm.model.Movie
import com.example.cinematv_mvvm.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActorDetailedViewModel @Inject constructor(private val baseCloudRepository: BaseCloudRepository): BaseViewModel() {

    val person = MutableLiveData(Person())
    val personMovies = MutableLiveData(ArrayList<Movie>())

    fun getPersonData(actor_id: Int){
        launchIO {
            when(val wrapper =  baseCloudRepository.getPersonById(actor_id)){
                is ResultWrapper.Success -> {
                    person.postValue(wrapper.value)
                }
                is ResultWrapper.Error -> {

                }
            }
        }
    }

    fun getPersonMovies(actor_id: Int){
        launchIO {
            when(val wrapper = baseCloudRepository.getPersonMoviesById(actor_id)){
                is ResultWrapper.Success ->{
                    personMovies.postValue(wrapper.value.cast)
                }
                is ResultWrapper.Error ->{}

            }
        }
    }

}