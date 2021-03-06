package com.example.cinematv_mvvm.model

data class Movie(
    var isAdult: Boolean = false,
    var backdrop_path: String? = null,
    var belongs_to_collection: Collection? = null,
    var budget: String? = null,
    var genres: Array<Genre> = arrayOf(),
    var homepage: String? = null,
    var id: Int = 0,
    var imdb_id: String? = null,
    var original_language: String? = null,
    var original_title: String? = null,
    var overview: String? = null,
    var popularity: Double = 0.0,
    var poster_path: String? = null,
    var production_companies: Array<ProductionCompanies> = arrayOf(),
    var production_countries: Array<ProductionCountries> = arrayOf(),
    var release_date: String? = null,
    var revenue: String? = null,
    var runtime: Int = 0,
    var status: String? = null,
    val genre_ids: IntArray = intArrayOf(),
    var tagline: String? = null,
    var title: String? = null,
    var isVideo: Boolean = false,
    var vote_average: Double = 0.0,
    var vote_count: Int = 0,
    val character: String? = null,
    val credit_id: String? = null,
    val order: Int = 0
)