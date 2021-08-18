package com.example.cinematv_mvvm.data.prefs

import android.content.SharedPreferences
import android.util.Log
import com.example.cinematv_mvvm.model.Movie
import com.google.gson.Gson
import javax.inject.Inject

class PrefsImpl @Inject constructor(private val gson: Gson,private val preferences: SharedPreferences) : Prefs {

    override fun isUserFirstTime(): Boolean {
        val isFirstTime = preferences.getBoolean("firstTime", true)

        if (isFirstTime){
            setNotFirstTime()
        }
        return isFirstTime
    }

    override fun setNotFirstTime() {
        val editor = preferences.edit()
        Log.i("check fors first time", "setNotFirstTime: ")
        editor.putString("Movie", gson.toJson(ArrayList<Movie>()))
        editor.putBoolean("firstTime", false)
        editor.putBoolean("auth", false)
        editor.putBoolean("loggedIn", false)
        editor.putString("sessionId", "")
        editor.apply()
    }

    override fun isAuthorized(): Boolean {
        return preferences.getBoolean("auth", false)
    }

    override fun setAuthorized(isAuthorized: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean("auth", isAuthorized)
        editor.apply()
    }

    override fun getSessionId(): String {
        return preferences.getString("sessionId", "").toString()
    }

    override fun setSessionId(sessionId: String) {
        val editor = preferences.edit()
        editor.putString("sessionId", sessionId)
        editor.apply()
    }

    override fun isLoggedIn(): Boolean {
        return preferences.getBoolean("loggedIn", false)
    }

    override fun login() {
        val editor = preferences.edit()
        editor.putBoolean("loggedIn", true)
    }

    override fun logout() {
        val editor = preferences.edit()
        editor.putBoolean("loggedIn", false)
    }

    override fun addFavoriteMovie(movie: Movie) {
        val favoriteMovies = getMovies()
        favoriteMovies.add(movie)
        val editor = preferences.edit()
        val json = gson.toJson(favoriteMovies)
        editor.putString("Movie", json)
        editor.apply()
    }

    override fun deleteFavoriteMovie(movie: Movie) {
        val favoriteMovies = getMovies()
        favoriteMovies.removeAt(indexOfMovie(movie))
        val editor = preferences.edit()
        val json = gson.toJson(favoriteMovies)
        editor.putString("Movie", json)
        editor.apply()
    }


    override fun getMovies(): ArrayList<Movie> {
        val json = preferences.getString("Movie","").orEmpty()
        return gson.fromJson(json, Array<Movie>::class.java).toCollection(ArrayList())
    }

    private fun indexOfMovie(searchMovie: Movie): Int{
        val favoriteMovies = getMovies()
        var index = 0
        for (movie in favoriteMovies){
            if (searchMovie.id == movie.id){
                return index
            }
            index++
        }
        return -1
    }


}