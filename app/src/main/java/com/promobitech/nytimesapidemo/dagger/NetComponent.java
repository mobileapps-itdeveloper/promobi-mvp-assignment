package com.promobitech.nytimesapidemo.dagger;

import com.promobitech.nytimesapidemo.TimesMovieReviewActivity;
import com.promobitech.nytimesapidemo.mvp.MovieReviewIntractor;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(TimesMovieReviewActivity activity);
    void inject(MovieReviewIntractor intractor);

}
