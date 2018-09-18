package com.promobitech.nytimesapidemo.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.promobitech.nytimesapidemo.classe.AppConstant;
import com.promobitech.nytimesapidemo.room.convertors.LinkTypeConverters;
import com.promobitech.nytimesapidemo.room.convertors.MultimediaTypeConverters;
import com.promobitech.nytimesapidemo.room.tables.Movie;
import com.promobitech.nytimesapidemo.server.dao.MovieDao;

@Database(entities = {Movie.class,
}, version = AppConstant.DB_VERSION, exportSchema = false)
@android.arch.persistence.room.TypeConverters({LinkTypeConverters.class,MultimediaTypeConverters.class})
public abstract class NYTimesDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}
