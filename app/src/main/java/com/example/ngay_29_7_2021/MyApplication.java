package com.example.ngay_29_7_2021;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration configuration =
                new RealmConfiguration.Builder()
                        .name("myFourthRealm")
                        .build();

        Realm.setDefaultConfiguration(configuration);
    }
}
