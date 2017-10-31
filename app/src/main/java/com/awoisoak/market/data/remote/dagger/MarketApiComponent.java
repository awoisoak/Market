package com.awoisoak.market.data.remote.dagger;


import com.awoisoak.market.data.remote.MarketApi;
import com.awoisoak.market.utils.dagger.AppComponent;
import com.awoisoak.market.utils.dagger.ApplicationModule;

import dagger.Component;

@Component(modules = {MarketApiModule.class, ApplicationModule.class})
public interface MarketApiComponent extends AppComponent {
    MarketApi getGiphyApi();
}
