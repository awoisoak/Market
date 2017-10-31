package com.awoisoak.market.data.remote;

import com.awoisoak.market.data.remote.impl.responses.MarketResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;


/**
 * Class to attack to the real Market API
 * TODO The implementation of this class is not included
 */


public class MarketManager implements MarketApi {

    private static MarketManager mInstance;

    public static MarketManager getmInstance() {
        if (mInstance == null) {
            mInstance = new MarketManager();
        }
        return mInstance;
    }

    @Override
    public Observable request(String category, int offset) {
        //empty Observable
        return Observable.create((ObservableEmitter<MarketResponse> emitter) ->{});

    }
}
