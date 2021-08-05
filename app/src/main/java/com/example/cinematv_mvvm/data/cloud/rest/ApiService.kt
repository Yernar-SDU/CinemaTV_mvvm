package com.example.cinematv_mvvm.data.cloud.rest

import com.example.cinematv_mvvm.core.Config
import com.example.cinematv_mvvm.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/movie/week" + Config.API_KEY )
    suspend fun getTrendingMovies(
        @Query("page") page: Int?
    ): JsonResults


    @GET("genre/movie/list" + Config.API_KEY )
    suspend fun getGenres(): JsonGenres

    @GET("movie/{movie_id}" + Config.API_KEY)
    suspend fun getMovieById(@Path("movie_id") movie_id: Int): Movie

    @GET("movie/{movie_id}/credits" + Config.API_KEY)
    suspend fun getCreditsMovieById(@Path("movie_id") movie_id: Int): MovieCredit

    @GET("movie/{movie_id}/videos" + Config.API_KEY)
    suspend fun getVideoMovieById(@Path("movie_id") movie_id: Int): VideoTrailer

    @GET("person/{person_id}" + Config.API_KEY)
    suspend fun getPersonById(@Path("person_id") actor_id: Int): Person

    @GET("person/{person_id}/movie_credits" + Config.API_KEY)
    suspend fun getPersonMovies(@Path("person_id") person_id: Int): CreditsMovie

    @GET("person/{person_id}" + Config.API_KEY)
    suspend fun getPersonDetails(@Path("person_id") person_id: Int): Person

    @GET("movie/popular" + Config.API_KEY + "&language=en-US")
    suspend fun getPopularMovieByPage(@Query("page") page: Int): JsonResults

    @GET("movie/top_rated" + Config.API_KEY + "&language=en-US")
    suspend fun getTopRatedMovieByPage(@Query("page") page: Int): JsonResults


    @GET("movie/upcoming" + Config.API_KEY + "&language=en-US")
    suspend fun getUpcomingMovieByPage(@Query("page") page: Int): JsonResults


    @GET("search/movie" + Config.API_KEY)
    suspend fun getMoviesSearch(@Query("page") page: Int, @Query("query") query: String): JsonResults

    @GET("authentication/token/new" + Config.API_KEY)
    suspend fun getRequestToken()



}