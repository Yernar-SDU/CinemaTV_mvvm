package com.example.cinematv_mvvm.data.prefs

import com.example.cinematv_mvvm.model.Movie

interface Prefs {
    fun addFavoriteMovie(movie: Movie)
    fun deleteFavoriteMovie(movie: Movie)
    fun getMovies(): ArrayList<Movie>

    fun isUserFirstTime(): Boolean
    fun setNotFirstTime()

    fun isAuthorized(): Boolean
    fun setAuthorized(isAuthorized: Boolean)

    fun isLoggedIn(): Boolean
    fun login()
    fun logout()
}