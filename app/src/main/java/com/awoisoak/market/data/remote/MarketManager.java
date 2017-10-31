package com.awoisoak.market.data.remote;

import io.reactivex.Observable;


/**
 * Class to attack to the real Market API
 * The implementation of this class is not included on this test
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
        //TODO
        return null;
    }
}
