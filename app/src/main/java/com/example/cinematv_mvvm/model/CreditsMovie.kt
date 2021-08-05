package com.example.cinematv_mvvm.model

data class CreditsMovie (
    var cast: ArrayList<Movie> = arrayListOf(),
    var crew: ArrayList<Movie> = arrayListOf()
)