package com.example.cinematv_mvvm.data.cloud.repository

import com.example.cinematv_mvvm.data.cloud.ResultWrapper
import com.example.cinematv_mvvm.data.cloud.rest.ApiService
import com.example.cinematv_mvvm.data.cloud.safeApiCall
import com.example.cinematv_mvvm.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CloudRepository(
    private val api: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): BaseCloudRepository {


    override suspend fun getTrendingMovies(page: Int): ResultWrapper<JsonResults> {
        return safeApiCall(dispatcher) {
            api.getTrendingMovies(page)
        }
    }


    override suspend fun getGenres(): ResultWrapper<JsonGenres> {
        return safeApiCall(dispatcher) {
            api.getGenres()
        }
    }

    override suspend fun getMovieById(movie_id: Int): ResultWrapper<Movie> {
        return safeApiCall(dispatcher){
            api.getMovieById(movie_id)
        }
    }

    override suspend fun getCreditsById(movie_id: Int): ResultWrapper<MovieCredit> {
        return safeApiCall(dispatcher){
            api.getCreditsMovieById(movie_id)
        }
    }

    override suspend fun getMovieVideoById(movie_id: Int): ResultWrapper<VideoTrailer> {
        return safeApiCall(dispatcher){
            api.getVideoMovieById(movie_id)
        }
    }

    override suspend fun getPersonById(actor_id: Int): ResultWrapper<Person> {
        return safeApiCall(dispatcher){
            api.getPersonById(actor_id)
        }
    }

    override suspend fun getPersonMoviesById(actor_id: Int): ResultWrapper<CreditsMovie> {
        return safeApiCall(dispatcher){
            api.getPersonMovies(actor_id)
        }
    }

    override suspend fun getPopularMoviesByPage(page: Int): ResultWrapper<JsonResults> {
        return safeApiCall(dispatcher){
            api.getPopularMovieByPage(page)
        }
    }

    override suspend fun getTopRatedMoviesByPage(page: Int): ResultWrapper<JsonResults> {
        return safeApiCall(dispatcher){
            api.getTopRatedMovieByPage(page)
        }
    }

    override suspend fun getUpcomingMoviesByPage(page: Int): ResultWrapper<JsonResults> {
        return safeApiCall(dispatcher){
            api.getUpcomingMovieByPage(page)
        }
    }

    override suspend fun getMoviesSearch(page: Int, query: String): ResultWrapper<JsonResults> {
        return safeApiCall(dispatcher){
            api.getMoviesSearch(page, query)
        }
    }

    override suspend fun getRequestToken(): ResultWrapper<LoginValidationSuccess> {
        return safeApiCall(dispatcher){
            api.getRequestToken()
        }
    }

    override suspend fun getSessionId(loginValidation: LoginValidation): ResultWrapper<LoginValidationSuccess> {
        return safeApiCall(dispatcher){
            api.getSessionId(loginValidation)
        }
    }

    override suspend fun login(loginValidation: LoginValidation): ResultWrapper<LoginValidationSuccess> {
        return safeApiCall(dispatcher){
            api.login(loginValidation)
        }
    }

    override suspend fun authenticate(requestToken: String): ResultWrapper<Unit> {
        return safeApiCall(dispatcher){
            api.authenticate(requestToken)
        }
    }

    override suspend fun getAccountDetails(session_id: String): ResultWrapper<User> {
        return safeApiCall(dispatcher){
            api.getAccountDetails(session_id)
        }
    }
}