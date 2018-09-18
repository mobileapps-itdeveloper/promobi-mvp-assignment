package com.promobitech.nytimesapidemo.mvp;

import com.promobitech.nytimesapidemo.room.tables.Movie;

import java.util.List;

/**
 * Created by khurram on 25/03/18.
 */
public interface IMovieReviewView {
    void hideProgress();

    void showProgress();

    void showMessages(String message);

    void onFailure(String appErrorMessage);

    void getListSuccess(List<Movie> movies);
}
