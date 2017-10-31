package com.awoisoak.market.presentation.main.videogamesfragment.dagger;

import com.awoisoak.market.data.remote.MarketApi;
import com.awoisoak.market.domain.interactors.ProductsRequestInteractor;
import com.awoisoak.market.domain.interactors.impl.ProductsRequestInteractorImpl;
import com.awoisoak.market.presentation.main.videogamesfragment.VideogamesPresenter;
import com.awoisoak.market.presentation.main.videogamesfragment.VideogamesView;
import com.awoisoak.market.presentation.main.videogamesfragment.impl.VideogamesPresenterImpl;
import com.awoisoak.market.utils.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class VideogamesModule {
    private final VideogamesView mView;

    public VideogamesModule(VideogamesView view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    ProductsRequestInteractor provideProductsRequestInteractor(MarketApi api) {
        return new ProductsRequestInteractorImpl(api);
    }


    @Provides
    @ActivityScope
    VideogamesPresenter provideVideogamesPresenter(ProductsRequestInteractor serverInteractor) {
        return new VideogamesPresenterImpl(mView, serverInteractor);
    }
}
