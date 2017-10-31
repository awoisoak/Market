package com.awoisoak.market.data.remote;

import com.awoisoak.market.data.remote.impl.responses.ErrorResponse;

/**
 * Market Listener
 */
public interface MarketListener<T> {
    /**
     * Called when the HTTP response is in the range [200..300).
     * @param response
     */
    void onResponse(T response);

    /**
     * Called when there was an error
     * @param error
     */
    void onError(ErrorResponse error);
}
