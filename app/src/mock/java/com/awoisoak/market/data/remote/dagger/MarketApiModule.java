package com.awoisoak.market.data.remote.dagger;


import com.awoisoak.market.data.remote.MarketApi;
import com.awoisoak.market.data.remote.MockMarketManager;

import dagger.Module;
import dagger.Provides;

@Module
public class MarketApiModule {

    @Provides
    MarketApi provideMarketAPI() {
        return MockMarketManager.getInstance();
    }
}
