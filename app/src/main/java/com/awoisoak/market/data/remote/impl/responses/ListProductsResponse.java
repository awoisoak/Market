package com.awoisoak.market.data.remote.impl.responses;


import com.awoisoak.market.data.Product;

import java.util.List;

/**
 * Response for a List Products Requests
 */
public class ListProductsResponse extends MarketResponse {

    private String category;
    private List<Product> list;

    public ListProductsResponse(String category,List<Product> list) {
        this.category = category;
        this.list = list;
    }

    public List<Product> getList() {
        return list;
    }

    public String getCategory() {
        return category;
    }
}
