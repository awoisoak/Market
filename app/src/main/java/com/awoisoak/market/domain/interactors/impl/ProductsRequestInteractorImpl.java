package com.awoisoak.market.domain.interactors.impl;


import com.awoisoak.market.data.remote.MarketApi;
import com.awoisoak.market.data.remote.impl.responses.ListProductsResponse;
import com.awoisoak.market.domain.interactors.ProductsRequestInteractor;
import com.awoisoak.market.utils.signals.RxBus;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * Interactor in charge of communicating with the Market server.
 * It must be called in a background thread.
 * <p>
 * (All classes in the Domain layer must be platform independent)
 */
public class ProductsRequestInteractorImpl implements ProductsRequestInteractor {

    private MarketApi api;

    @Inject
    public ProductsRequestInteractorImpl(MarketApi api) {
        this.api = api;
    }


    @Override
    public void getProducts(String category, int offset) {
        Observable<ListProductsResponse> observable =api.request(category,offset);
        observable
                .observeOn(Schedulers.io())
                .doOnNext(listProductResponse ->
                        RxBus.getInstance().send(listProductResponse))
                .doOnError(throwable ->
                        RxBus.getInstance().send(throwable))
                .subscribe();
    }

}
