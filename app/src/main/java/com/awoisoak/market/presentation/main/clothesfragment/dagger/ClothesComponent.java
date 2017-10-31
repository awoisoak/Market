package com.awoisoak.market.presentation.main.clothesfragment.dagger;

import com.awoisoak.market.data.remote.dagger.MarketApiComponent;
import com.awoisoak.market.presentation.main.clothesfragment.impl.ClothesFragment;
import com.awoisoak.market.utils.dagger.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {MarketApiComponent.class}, modules = ClothesModule.class)
public interface ClothesComponent {
    /**
     * It will inject the ClothesPresenter returned in ClothesModule
     * in the variable with the @Inject annotation in ClothesFragment
     * (This method could have any other name, it will just says that will inject the dependencies
     * given in ClothesModule
     * into the variables with the @Inject annotation in ClothesFragment)
     */
    void inject(ClothesFragment o);
}
