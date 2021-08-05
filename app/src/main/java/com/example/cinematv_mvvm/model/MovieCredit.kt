package com.example.cinematv_mvvm.model

data class MovieCredit(
    var id: Int = 0,
    var cast: ArrayList<Person> = arrayListOf(),
    var crew: ArrayList<Person> = arrayListOf()
)