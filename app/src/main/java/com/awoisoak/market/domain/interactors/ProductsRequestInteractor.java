package com.awoisoak.market.domain.interactors;


import com.awoisoak.market.data.remote.MarketApi;

public interface ProductsRequestInteractor {

    /**
     * Get all products for the passed category.
     * The maximum number of records to retrieved per query is
     * {@link MarketApi#MAX_NUMBER_PRODUCTS_RETURNED}
     */
    void getProducts(String category, int offset);

}
