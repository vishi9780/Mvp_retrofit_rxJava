
package com.example.movieapp.movie_list;

import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieListResponse;
import com.example.movieapp.network.ApiClient;
import com.example.movieapp.network.ApiInterface;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.CallbackCompletableObserver;
import io.reactivex.internal.operators.flowable.FlowableOnErrorNext;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movieapp.network.ApiClient.API_KEY;
import static com.example.movieapp.network.ApiClient.compositeDisposable;

public class MovieListModel implements MovieListContract.Model {

    private final String TAG = "MovieListModel";


    /**
     * This function will fetch movies data
     *
     * @param onFinishedListener
     * @param pageNo             : Which page to load.
     */
    @Override
    public void getMovieList(final OnFinishedListener onFinishedListener, int pageNo) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        ApiClient.compositeDisposable.add(apiService.getPopularMovies(API_KEY, pageNo)
                .delay(2000, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()
                ).subscribe(new Consumer<MovieListResponse>() {
                    @Override
                    public void accept(MovieListResponse movieListResponse) throws Exception {
                        List<Movie> movies = movieListResponse.getResults();
                        Log.d(TAG, "Number of movies received: " + movies.size());
                        onFinishedListener.onFinished(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onFinishedListener.onFailure(throwable);
                    }
                }));



//        Call<MovieListResponse> call = apiService.getPopularMovies(API_KEY, pageNo);
//        call.enqueue(new Callback<MovieListResponse>() {
//            @Override
//            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
//                List<Movie> movies = response.body().getResults();
//                Log.d(TAG, "Number of movies received: " + movies.size());
//                onFinishedListener.onFinished(movies);
//            }
//
//            @Override
//            public void onFailure(Call<MovieListResponse> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//                onFinishedListener.onFailure(t);
//            }
//        });
    }

}
