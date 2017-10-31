package com.awoisoak.market.data.remote;


import io.reactivex.Observable;


/**
 * Interface to retrieve products from Market API
 */

public interface MarketApi {

    String CATEGORY_ALL = "ALL";
    String CATEGORY_MEN = "MEN";
    String CATEGORY_WOMEN = "WOMEN";

    /**
     * Max number of products returned by request
     */
    int MAX_NUMBER_PRODUCTS_RETURNED = 20;


    /**
     * Request all Market products for the category given.
     * The maximum number of records to return per query is
     * {@link MarketApi#MAX_NUMBER_PRODUCTS_RETURNED}
     *
     * @param category, category of products to be requested
     * @param offset,   An optional results offset. Defaults to 0.
     */
    Observable request(String category, int offset);

}
