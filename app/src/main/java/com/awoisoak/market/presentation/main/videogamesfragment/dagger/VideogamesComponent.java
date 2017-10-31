package com.awoisoak.market.presentation.main.videogamesfragment.dagger;

import com.awoisoak.market.data.remote.dagger.MarketApiComponent;
import com.awoisoak.market.presentation.main.videogamesfragment.impl.VideogamesFragment;
import com.awoisoak.market.utils.dagger.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {MarketApiComponent.class}, modules = VideogamesModule.class)
public interface VideogamesComponent {
    /**
     * It will inject the ClothesPresenter returned in ClothesModule
     * in the variable with the @Inject annotation in ClothesFragment
     * (This method could have any other name, it will just says that will inject the dependencies
     * given in ClothesModule
     * into the variables with the @Inject annotation in ClothesFragment)
     */
    void inject(VideogamesFragment o);
}
