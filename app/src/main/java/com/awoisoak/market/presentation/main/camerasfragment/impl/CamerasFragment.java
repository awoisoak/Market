package com.awoisoak.market.presentation.main.camerasfragment.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awoisoak.market.R;
import com.awoisoak.market.data.Product;
import com.awoisoak.market.presentation.MarketApplication;
import com.awoisoak.market.presentation.main.camerasfragment.dagger.DaggerCamerasComponent;
import com.awoisoak.market.presentation.main.camerasfragment.CamerasPresenter;
import com.awoisoak.market.presentation.main.camerasfragment.CamerasView;
import com.awoisoak.market.presentation.main.camerasfragment.dagger.CamerasModule;
import com.awoisoak.market.utils.threading.ThreadPool;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Men category Products Fragment.
 */
public class CamerasFragment extends Fragment implements CamerasView,
        CamerasAdapter.ProductItemClickListener {

    public static final String TAG = CamerasFragment.class.getSimpleName();
    @BindView(R.id.cameras_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.cameras_text_view) TextView mLoadingText;
    @BindView(R.id.cameras_recycler)

    RecyclerView mRecyclerView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Snackbar mSnackbar;
    private GridLayoutManager mLayoutManager;
    private CamerasAdapter mAdapter;
    @Inject
    CamerasPresenter mPresenter;

    public CamerasFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static CamerasFragment newInstance(int sectionNumber) {
        CamerasFragment fragment = new CamerasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDagger();
        mPresenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cameras_fragment, container, false);
        ButterKnife.bind(this, rootView);
        mLayoutManager = new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.num_columns),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        addOnScrollListener();
        mPresenter.onCreateView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    /**
     * Method to detect when the RecyclerView bottom is reached
     */
    private void addOnScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    if (!mRecyclerView.canScrollVertically(1)) {
                        mPresenter.onBottomReached();
                    }
                }
            }
        });
    }

    private void initDagger() {
        DaggerCamerasComponent.builder()
                .camerasModule(new CamerasModule(this))
                .marketApiComponent(
                        (MarketApplication.getMarketApplication()).getMarketApiComponent())
                .build().inject(this);
    }


    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mLoadingText.setVisibility(View.GONE);
    }

    @Override
    public void bindProductsList(List<Product> products) {
        mAdapter = new CamerasAdapter(products, this, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateProductsList(List<Product> products) {

        if (mAdapter != null) {
            /**
             * We execute like this because of the next bug
             * http://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-method
             * -in-a-scroll-callback-recyclerview-v724-2
             */
            mRecyclerView.post(new Runnable() {
                public void run() {
                    /**
                     * We don't use notifyItemRangeInserted because we keep replicating this
                     * known Android bug
                     * https://issuetracker.google.com/issues/37007605
                     */
                    mAdapter.notifyDataSetChanged();
                }
            });
        } else {
            Log.e(TAG, "updateGifList | mAdapter is null!");
        }
    }

    @Override
    public void showLoadingSnackbar() {
        mSnackbar = Snackbar.make(getActivity().findViewById(R.id.personalized_fab),
                getResources().getString(R.string.loading_products),
                Snackbar.LENGTH_INDEFINITE);
        mSnackbar.getView().setBackgroundColor(
                ContextCompat.getColor(getActivity(), R.color.colorAccent));
        mSnackbar.show();
    }

    @Override
    public void showErrorSnackbar() {
        mSnackbar = Snackbar.make(getActivity().findViewById(R.id.personalized_fab),
                getResources().getString(R.string.error_network_connection),
                Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
                showLoadingSnackbar();
                mPresenter.onRetryProductsRequest();
            }
        });
        mSnackbar.show();
    }

    @Override
    public void hideSnackbar() {
        if (mSnackbar != null) {
            mSnackbar.dismiss();
        }
    }


    @Override
    public void showToast(final String message) {
        ThreadPool.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onFavouriteProductItemClick(Product product) {
        ThreadPool.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), R.string.likes_feature_not_implemented,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCommentsProductItemClick(Product product) {
        ThreadPool.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), R.string.comments_feature_not_implemented,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onImageProductItemClick(Product product) {
        ThreadPool.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Product item Activity not implemented",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
