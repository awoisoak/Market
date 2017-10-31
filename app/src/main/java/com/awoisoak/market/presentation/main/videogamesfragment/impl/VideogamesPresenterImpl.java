package com.awoisoak.market.presentation.main.videogamesfragment.impl;


import android.util.Log;

import com.awoisoak.market.R;
import com.awoisoak.market.data.Product;
import com.awoisoak.market.data.remote.MarketApi;
import com.awoisoak.market.data.remote.impl.responses.ListProductsResponse;
import com.awoisoak.market.domain.interactors.ProductsRequestInteractor;
import com.awoisoak.market.presentation.main.MainActivity;
import com.awoisoak.market.presentation.main.VisibleEvent;
import com.awoisoak.market.presentation.main.videogamesfragment.VideogamesPresenter;
import com.awoisoak.market.presentation.main.videogamesfragment.VideogamesView;
import com.awoisoak.market.utils.signals.RxBus;
import com.awoisoak.market.utils.threading.ThreadPool;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VideogamesPresenterImpl implements VideogamesPresenter {
    public static String TAG = VideogamesPresenterImpl.class.getSimpleName();

    private VideogamesView mView;
    private ProductsRequestInteractor mServerInteractor;
    private List<Product> mProducts = new ArrayList<>();

    private boolean mVideogamesDownloaded;
    private boolean mIsProductsRequestRunning;

    private int mOffset;
    private boolean mIsFirstRequest = true;


    public VideogamesPresenterImpl(VideogamesView view,
            ProductsRequestInteractor serverInteractor) {
        mView = view;
        mServerInteractor = serverInteractor;
    }

    @Override
    public void onCreate() {
        //TODO the error response might be received by the others fragments (we should check the
        // category)
        //But in order to do that, we would need to pass the error response instead of a Throwable
        RxBus.getInstance().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                            if (o instanceof ListProductsResponse) {
                                onProductsReceived((ListProductsResponse) o);
                            } else if (o instanceof Throwable) {
                                onErrorRetrievingProducts((Throwable) o);
                            } else if (o instanceof VisibleEvent) {
                                onVisibleEvent((VisibleEvent) o);
                            } else {
                                Log.e(TAG, "unexpected value sent by RXBus!");
                            }
                        }
                );
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void onDestroy() {
        RxBus.getInstance().toObservable().unsubscribeOn(Schedulers.io());
        mView = null;
    }


    private void requestNewProducts() {
        if (!mIsProductsRequestRunning) {
            if (!mIsFirstRequest) {
                mView.showLoadingSnackbar();
            }
            mIsProductsRequestRunning = true;
            ThreadPool.run(new Runnable() {
                @Override
                public void run() {
                    mServerInteractor.getProducts(MarketApi.CATEGORY_WOMEN, mOffset);
                }
            });
        }
    }


    @Override
    public void onRetryProductsRequest() {
        System.out.println("awooooo | onRetryProductsRequest| Calling requestNewProducts...");

        requestNewProducts();
    }

    public void increaseOffset() {
        mOffset += MarketApi.MAX_NUMBER_PRODUCTS_RETURNED;
    }


    @Override
    public void onBottomReached() {
        if (!mVideogamesDownloaded) {
            requestNewProducts();
        }
    }

    /**
     * This method will be called when the interactor returns the new products
     */
    public void onProductsReceived(final ListProductsResponse response) {
        //We are only interested on Products received from ALL category
        if (!response.getCategory().equals(MarketApi.CATEGORY_WOMEN)) {
            return;
        }

        if (response.getList().size() == 0) {
            mView.showToast(
                    ((VideogamesFragment) mView).getResources().getString(
                            R.string.no_products_found));
            return;
        }

        //We make sure bind a new list against the adapter
        if (mIsFirstRequest) {
            mProducts.clear();
        }

        //Add the new products to the array and increase the offset
        mProducts.addAll(response.getList());

        increaseOffset();


        ThreadPool.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.hideSnackbar();
                mView.hideProgressBar();
                if (mIsFirstRequest) {
                    mView.bindProductsList(mProducts);
                    mIsFirstRequest = false;
                } else {
                    mView.updateProductsList(response.getList());
                    mView.hideSnackbar();
                }
                mIsProductsRequestRunning = false;
                if (mOffset >= response.getTotalRecords()) {
                    mVideogamesDownloaded = true;
                }
            }
        });
    }

    /**
     * This method will be called when the interactor returns an error trying to get the new
     * products
     */
    public void onErrorRetrievingProducts(Throwable response) {
        //TODO
//        if (!response.getCategory().equals(MarketApi.CATEGORY_WOMEN)) {
//            return;
//        }
        mIsProductsRequestRunning = false;
        ThreadPool.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.hideProgressBar();
                mView.showErrorSnackbar();
            }
        });
    }

    /**
     * This method will be called when the fragment is visible for the user
     */
    public void onVisibleEvent(final VisibleEvent event) {
        if (event.getPosition() == MainActivity.VIDEO_GAMES_TAB && mIsFirstRequest) {
            Log.d(TAG, "awooooo @BUS | onVisibleEvent ");
            requestNewProducts();
        }
    }


    @Override
    public void onAttach() {

    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDetach() {

    }
}
