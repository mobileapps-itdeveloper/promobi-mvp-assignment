package com.promobitech.nytimesapidemo.dagger;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.promobitech.nytimesapidemo.classe.AppConstant;
import com.promobitech.nytimesapidemo.room.NYTimesDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {
    String mBaseUrl;

    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
        return gsonConverterFactory;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        client.addNetworkInterceptor(new StethoInterceptor());
        client.cache(cache);
        client.followRedirects(false);
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory converterFactory, OkHttpClient okHttpClient) {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder().baseUrl(AppConstant.DOMAIN).client(okHttpClient)
                /**This RxJava2CallAdapterFactory will enable retrofit with RxJava */
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory).build();
        return retrofit;
    }

    @Provides
    @Singleton
    NYTimesDatabase provideDatabase(Application application) {
        NYTimesDatabase db = Room.databaseBuilder(application,
                NYTimesDatabase.class, "DaelFactoryDatabase").build();
        return db;
    }
}
