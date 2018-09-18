package com.promobitech.nytimesapidemo.mvp;

import android.app.Application;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.util.Log;

import com.promobitech.nytimesapidemo.classe.AppConstant;
import com.promobitech.nytimesapidemo.classe.NYTimes;
import com.promobitech.nytimesapidemo.classe.NetworkUtils;
import com.promobitech.nytimesapidemo.retrofit.APIInterface;
import com.promobitech.nytimesapidemo.room.NYTimesDatabase;
import com.promobitech.nytimesapidemo.room.tables.Movie;
import com.promobitech.nytimesapidemo.server.ResponseResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MovieReviewIntractor implements IMovieReviewModel {
    public static final String TAG = "MovieReviewIntractor";
    @Inject
    Retrofit retrofit;
    @Inject
    NYTimesDatabase db;
    @Inject
    Application context;

    public MovieReviewIntractor() {
        NYTimes.getNetComponent().inject(this);

    }

    @Override
    public void filterSearech(String publicationDate, String openingDate, String reviewer, OnFinshListner onFinshListner) {
        if (NetworkUtils.isNetworkConnected(context)) {
            APIInterface apiInterface = retrofit.create(APIInterface.class);
            /**
             * We can implement more complex implementation with more parameters, I used simple basic parameter only
             */
            apiInterface.getMovieReviews(AppConstant.API_KEY, publicationDate, openingDate, reviewer)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseResult>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.i(TAG, "onSubscribe");
                        }

                        @Override
                        public void onNext(ResponseResult responseResult) {
                            Log.i(TAG, "onNext");
                            Completable.fromAction(() -> db.getMovieDao().insertAll(responseResult.getResults())).subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.i(TAG, "onComplete insertion into db");
                                        onFinshListner.onSuccess(responseResult.getResults(), false);
                                    });
                        }

                        @Override
                        public void onError(Throwable e) {
                            onFinshListner.onFailuar(e.getMessage());
                            Log.i(TAG, "onError");
                        }

                        @Override
                        public void onComplete() {
                            Log.i(TAG, "onComplete");
                        }
                    });
        } else {
            Single<List<Movie>> listSingle;
            List<Object> args = new ArrayList<>();
            String queryString = "SELECT * FROM Movie";
            /**
             * Keep in mind date is like query so no sharep date results
             */
            if (publicationDate != null) {
                queryString += " WHERE";
                queryString += " publication_date LIKE ?%";
                args.add(publicationDate);
            }
            if (openingDate != null) {
                queryString += " WHERE";
                queryString += " opening_date LIKE ?%";
                args.add(openingDate);
            }
            if (reviewer != null) {
                queryString += " WHERE";
                queryString += " byline LIKE ?%";
                args.add(reviewer);
            }
            SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
            Log.i(TAG, "Local query : " + query.toString());
            listSingle = db.getMovieDao().getAllList(query);
            listSingle
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<Movie>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onSuccess(List<Movie> movies) {
                            Log.i(TAG, "onSuccess Data retrive from local db");
                            onFinshListner.onSuccess(movies, true);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, " Data retrive from local db onError");
                            onFinshListner.onFailuar(e.getMessage());
                        }
                    });
        }
    }
}
