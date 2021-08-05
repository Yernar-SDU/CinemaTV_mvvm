package com.example.cinematv_mvvm.model

data class Person(
    var isAdult: Boolean = false,
    var gender: Int = 0,
    var id: Int = 0,
    var known_for_department: String? = null,
    var name: String? = null,
    var original_name: String? = null,
    var popularity: Double = 0.0,
    var profile_path: String? = null,
    var cast_id: Int = 0,
    var character: String? = null,
    var credit_id: String? = null,
    var order: Int = 0,
    var department: String? = null,
    var job: String? = null
)