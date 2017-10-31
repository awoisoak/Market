package com.awoisoak.market.presentation;

import android.app.Application;


import com.awoisoak.market.data.remote.dagger.DaggerMarketApiComponent;
import com.awoisoak.market.data.remote.dagger.MarketApiComponent;
import com.awoisoak.market.utils.dagger.ApplicationModule;

import dagger.Module;

@Module
public class MarketApplication extends Application {
    private MarketApiComponent mMarketApiComponent;
    private static MarketApplication sMarketApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        sMarketApplication = this;
        mMarketApiComponent =
                DaggerMarketApiComponent.builder()
                        .applicationModule(new ApplicationModule(getApplicationContext())).build();
    }

    public MarketApiComponent getMarketApiComponent() {
        return mMarketApiComponent;
    }


    public static MarketApplication getMarketApplication() {
        if (sMarketApplication == null) {
            throw new NullPointerException("sMarketApplication is null");
        } else {
            return sMarketApplication;
        }
    }
}
