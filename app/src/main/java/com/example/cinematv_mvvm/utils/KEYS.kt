package com.example.cinematv_mvvm.utils

import kotlin.collections.HashMap

class KEYS {
    companion object{
        const val POSITION = "POSITION"

        val GENRES: HashMap<Int?, String?> = object : HashMap<Int?, String?>() {
            init {
                put(28, "Action")
                put(12, "Adventure")
                put(16, "Animation")
                put(35, "Comedy")
                put(80, "Crime")
                put(99, "Documentary")
                put(18, "Drama")
                put(10751, "Family")
                put(14, "Fantasy")
                put(36, "History")
                put(27, "Horror")
                put(10402, "Music")
                put(9648, "Mystery")
                put(10749, "Romance")
                put(878, "Science Fiction")
                put(10770, "TV com.example.cinematv_mvvm.model.Movie")
                put(53, "Thriller")
                put(10752, "War")
                put(37, "Western")
            }
        }


        val TAB_GENRES: HashMap<Int?, String?> = object : HashMap<Int?, String?>(){
            init {
                put(0,"Action")
                put(1,"Animation")
                put(2,"Comedy")
                put(3,"Adventure")
                put(4,"Drama")
            }
        }


        val FILM_REQUEST_PAGES: HashMap<String, Int> = object : HashMap<String, Int>(){
            init {
                put("Action",1)
                put("Animation",1)
                put("Comedy",1)
                put("Adventure",1)
                put("Drama",1)
                put("Search",1)
            }
        }

    }
}