package com.awoisoak.market.presentation.main.camerasfragment.dagger;

import com.awoisoak.market.data.remote.dagger.MarketApiComponent;
import com.awoisoak.market.presentation.main.camerasfragment.impl.CamerasFragment;
import com.awoisoak.market.utils.dagger.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {MarketApiComponent.class}, modules = CamerasModule.class)
public interface CamerasComponent {
    /**
     * It will inject the ClothesProductsPresenter returned in ClothesProductsModule
     * in the variable with the @Inject annotation in ClothesProductsFragment
     * (This method could have any other name, it will just says that will inject the dependencies
     * given in ClothesProductsModule
     * into the variables with the @Inject annotation in ClothesProductsFragment)
     */
    void inject(CamerasFragment o);
}
