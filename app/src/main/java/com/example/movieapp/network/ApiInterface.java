package com.example.movieapp.network;

import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieListResponse;

import io.reactivex.Flowable;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//    @GET("movie/popular")
//    Call<MovieListResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int PageNo);

    @GET("movie/popular")
    Flowable<MovieListResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int PageNo);

    /*@GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("append_to_response") String credits);\*/

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("append_to_response") String credits);

}
