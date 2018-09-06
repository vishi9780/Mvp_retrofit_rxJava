
package com.example.movieapp.movie_list;

import java.util.List;

import com.example.movieapp.model.Movie;

public interface MovieListContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<Movie> movieArrayList);

            void onFailure(Throwable t);
        }

        void getMovieList(OnFinishedListener onFinishedListener, int pageNo);

    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<Movie> movieArrayList);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();

    }
}
