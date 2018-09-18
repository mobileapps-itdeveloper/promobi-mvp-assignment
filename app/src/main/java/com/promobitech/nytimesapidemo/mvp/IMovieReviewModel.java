package com.promobitech.nytimesapidemo.mvp;

import com.promobitech.nytimesapidemo.room.tables.Movie;

import java.util.List;

/**
 * Created by khurram on 25/03/18.
 */

public interface IMovieReviewModel {
    interface OnFinshListner {
        void onSuccess(List<Movie> movies, boolean isOffline);
        void onFailuar(String error);
    }
    void filterSearech(String publicationDate, String openingDate,String reviewer , OnFinshListner onFinshListner);

}
