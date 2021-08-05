package com.example.cinematv_mvvm.ui.favorites

import androidx.lifecycle.MutableLiveData
import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.prefs.Prefs
import com.example.cinematv_mvvm.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val prefs: Prefs
): BaseViewModel() {
    val favoriteMovies = MutableLiveData(ArrayList<Movie>())


    fun getFavoriteMovies(){
        launchIO {
            favoriteMovies.postValue(prefs.getMovies())
        }
    }


}