package com.awoisoak.market.data.remote.impl.responses;

/**
 * Market Response
 */

public class MarketResponse {
    int code;
    int totalRecords;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
}
