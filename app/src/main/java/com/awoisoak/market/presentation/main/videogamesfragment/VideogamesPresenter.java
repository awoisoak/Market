package com.awoisoak.market.presentation.main.videogamesfragment;

import com.awoisoak.market.presentation.IFragmentPresenter;


public interface VideogamesPresenter extends IFragmentPresenter {

    /**
     * Called when the user choose to retry a request which had failed
     */
    void onRetryProductsRequest();


    /**
     * Called when the user reaches the bottom of the RecyclerView
     */
    void onBottomReached();


}
