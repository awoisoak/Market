package com.awoisoak.market.data.remote.impl.responses;

/**
 * Generic Error Response
 */

public class ErrorResponse extends MarketResponse {

    private String message;
    private String category;

    public ErrorResponse(String category, String message) {
        this.message = message;
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public String getCategory() {
        return category;
    }
}
