package com.promobitech.nytimesapidemo.server.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import com.promobitech.nytimesapidemo.room.tables.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> item);

    @RawQuery
    Single<List<Movie>> getAllList(SupportSQLiteQuery query);


}
