package com.awoisoak.market.presentation.main.camerasfragment.dagger;

import com.awoisoak.market.data.remote.MarketApi;
import com.awoisoak.market.domain.interactors.ProductsRequestInteractor;
import com.awoisoak.market.domain.interactors.impl.ProductsRequestInteractorImpl;
import com.awoisoak.market.presentation.main.camerasfragment.CamerasPresenter;
import com.awoisoak.market.presentation.main.camerasfragment.CamerasView;
import com.awoisoak.market.presentation.main.camerasfragment.impl.CamerasPresenterImpl;
import com.awoisoak.market.utils.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class CamerasModule {
    private final CamerasView mView;

    public CamerasModule(CamerasView view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    ProductsRequestInteractor provideProductsRequestInteractor(MarketApi api) {
        return new ProductsRequestInteractorImpl(api);
    }


    @Provides
    @ActivityScope
    CamerasPresenter provideCamerasPresenter(ProductsRequestInteractor serverInteractor) {
        return new CamerasPresenterImpl(mView, serverInteractor);
    }
}
