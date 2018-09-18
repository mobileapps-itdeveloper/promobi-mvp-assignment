package com.promobitech.nytimesapidemo.mvp;

import android.content.Context;

import com.promobitech.nytimesapidemo.R;
import com.promobitech.nytimesapidemo.room.tables.Movie;

import java.util.List;

public class MovieReviewPresenter implements IMovieReviewPresenter {
    private IMovieReviewModel iMovieReviewModel;
    private IMovieReviewView iMovieReviewView;
    private Context context;

    public MovieReviewPresenter(IMovieReviewModel iMovieReviewModel, IMovieReviewView iMovieReviewView,Context context) {
        this.iMovieReviewModel = iMovieReviewModel;
        this.iMovieReviewView = iMovieReviewView;
        this.context=context;
    }


    @Override
    public void filterButtonClick(String publicationDate, String openingDate, String reviewer) {
        if (publicationDate == null && openingDate == null && reviewer == null)
            iMovieReviewView.showMessages(context.getString(R.string.message_without_filter));
        else
            iMovieReviewView.showMessages("Searching for \npublicationDate: " + publicationDate + "\nopeningDate: " + openingDate + "\neviewer: ");
        iMovieReviewView.showProgress();

        iMovieReviewModel.filterSearech(publicationDate, openingDate, reviewer, new IMovieReviewModel.OnFinshListner() {
            @Override
            public void onSuccess(List<Movie> movies, boolean isOffline) {
                iMovieReviewView.hideProgress();
                iMovieReviewView.getListSuccess(movies);
                if(isOffline)iMovieReviewView.showMessages(context.getResources().getString(R.string.message_offline_database));
            }

            @Override
            public void onFailuar(String error) {
                iMovieReviewView.hideProgress();
                iMovieReviewView.onFailure(error);
            }
        });
    }
}
