package com.awoisoak.market.presentation.main.videogamesfragment;

import com.awoisoak.market.data.Product;

import java.util.List;


public interface VideogamesView {

    /**
     * Hide the initial ProgressBar displayed before the first products are retrieved
     */
    void hideProgressBar();

    /**
     * Bind the Products retrieved.
     * The implementation should create the adapter and set them to the RecyclerView
     */
    void bindProductsList(List<Product> products);

    /**
     * Update the adapter with the new products received
     */
    void updateProductsList(List<Product> products);

    /**
     * Display Snackbar to inform the user of new products being retrieved
     */
    void showLoadingSnackbar();


    /**
     * Display Error Snackbar to inform the user there was an error and ask if he/she want to retry.
     */
    void showErrorSnackbar();


    /**
     * Hide any of the previous Snackbar
     */
    void hideSnackbar();


    /**
     * Display a toast with the passed message
     */
    void showToast(String message);


}
