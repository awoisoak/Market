package com.awoisoak.market.presentation.main.clothesfragment.dagger;

import com.awoisoak.market.data.remote.MarketApi;
import com.awoisoak.market.domain.interactors.ProductsRequestInteractor;
import com.awoisoak.market.domain.interactors.impl.ProductsRequestInteractorImpl;
import com.awoisoak.market.presentation.main.clothesfragment.ClothesPresenter;
import com.awoisoak.market.presentation.main.clothesfragment.ClothesView;
import com.awoisoak.market.presentation.main.clothesfragment.impl.ClothesPresenterImpl;
import com.awoisoak.market.utils.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ClothesModule {
    private final ClothesView mView;

    public ClothesModule(ClothesView view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    ProductsRequestInteractor provideProductsRequestInteractor(MarketApi api) {
        return new ProductsRequestInteractorImpl(api);
    }


    @Provides
    @ActivityScope
    ClothesPresenter provideClothesPresenter(ProductsRequestInteractor serverInteractor) {
        return new ClothesPresenterImpl(mView, serverInteractor);
    }
}
