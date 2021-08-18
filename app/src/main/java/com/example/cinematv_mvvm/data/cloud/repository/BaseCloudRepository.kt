package com.example.cinematv_mvvm.data.cloud.repository

import com.example.cinematv_mvvm.data.cloud.ResultWrapper
import com.example.cinematv_mvvm.model.*

interface BaseCloudRepository{

    suspend fun getTrendingMovies(page: Int): ResultWrapper<JsonResults>

    suspend fun getGenres(): ResultWrapper<JsonGenres>

    suspend fun getMovieById(movie_id: Int): ResultWrapper<Movie>

    suspend fun getCreditsById(movie_id: Int): ResultWrapper<MovieCredit>

    suspend fun getMovieVideoById(movie_id: Int): ResultWrapper<VideoTrailer>

    suspend fun getPersonById(actor_id: Int): ResultWrapper<Person>

    suspend fun getPersonMoviesById(actor_id: Int): ResultWrapper<CreditsMovie>

    suspend fun getPopularMoviesByPage(page: Int): ResultWrapper<JsonResults>

    suspend fun getTopRatedMoviesByPage(page: Int): ResultWrapper<JsonResults>

    suspend fun getUpcomingMoviesByPage(page: Int): ResultWrapper<JsonResults>

    suspend fun getMoviesSearch(page: Int, query: String): ResultWrapper<JsonResults>

    suspend fun getRequestToken(): ResultWrapper<LoginValidationSuccess>

    suspend fun getSessionId(loginValidation: LoginValidation): ResultWrapper<LoginValidationSuccess>

    suspend fun login(loginValidation: LoginValidation): ResultWrapper<LoginValidationSuccess>

    suspend fun authenticate(requestToken: String): ResultWrapper<Unit>

    suspend fun getAccountDetails(session_id: String): ResultWrapper<User>
}