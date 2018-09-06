package com.example.movieapp.movie_details;

import android.util.Log;


import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieListResponse;
import com.example.movieapp.network.ApiClient;
import com.example.movieapp.network.ApiInterface;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movieapp.network.ApiClient.API_KEY;
import static com.example.movieapp.utils.Constants.CREDITS;

public class MovieDetailsModel implements MovieDetailsContract.Model {

    private final String TAG = "MovieDetailsModel";


    @Override
    public void getMovieDetails(final OnFinishedListener onFinishedListener, int movieId) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        ApiClient.compositeDisposable.add(apiService.getMovieDetails(movieId, API_KEY, CREDITS)
                .delay(2000, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()
                ).subscribe(new Consumer<Movie>() {
            @Override
            public void accept(Movie movie) throws Exception {

                Log.d(TAG, "Number of movies received: " + movie.getTitle());
                onFinishedListener.onFinished(movie);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                onFinishedListener.onFailure(throwable);
            }
        }));

//        Call<Movie> call = apiService.getMovieDetails(movieId, API_KEY, CREDITS);
//        call.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                Movie movie = response.body();
//                Log.d(TAG, "Movie data received: " + movie.toString());
//                onFinishedListener.onFinished(movie);
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//                Log.e(TAG, t.toString());
//                onFinishedListener.onFailure(t);
//            }
//        });

    }
}
