package com.promobitech.nytimesapidemo.classe;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.promobitech.nytimesapidemo.dagger.AppModule;
import com.promobitech.nytimesapidemo.dagger.DaggerNetComponent;
import com.promobitech.nytimesapidemo.dagger.NetComponent;
import com.promobitech.nytimesapidemo.dagger.NetModule;

import io.reactivex.plugins.RxJavaPlugins;

public class NYTimes extends Application {
    private static NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(AppConstant.DOMAIN))
                .appModule(new AppModule(this))
                .build();
        RxJavaPlugins.setErrorHandler(throwable -> Log.e("NYTimes", "Error Tracker" + throwable));
    }

    public static NetComponent getNetComponent() {
        return netComponent;
    }
}
