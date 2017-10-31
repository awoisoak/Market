package com.awoisoak.market.presentation.main.camerasfragment.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awoisoak.market.R;
import com.awoisoak.market.data.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CamerasAdapter extends RecyclerView.Adapter<CamerasAdapter.ProductViewHolder> {

    public static final String TAG = CamerasAdapter.class.getSimpleName();
    private List<Product> mProducts;
    private ProductItemClickListener mListener;
    private Context mContext;

    /**
     * Listener to detect when the user click on an specific Product button
     */
    public interface ProductItemClickListener {
        void onFavouriteProductItemClick(Product Product);
        void onCommentsProductItemClick(Product Product);
        void onImageProductItemClick(Product Product);
    }

    public CamerasAdapter(List<Product> products, ProductItemClickListener listener,
            Context context) {
        mProducts = products;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_cameras_list, parent, false);
        return new ProductViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bindItem(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_cameras_image) ImageView ivItem;
        @BindView(R.id.item_cameras_sold_out) ImageView ivSoldOut;
        @BindView(R.id.item_cameras_name) TextView name;
        @BindView(R.id.item_cameras_likes_button) ImageView likesButton;
        @BindView(R.id.item_cameras_comments_button) ImageView commentsButton;
        @BindView(R.id.item_cameras_comments_numbers) TextView numComments;
        @BindView(R.id.item_cameras_likes_numbers) TextView numLikes;
        @BindView(R.id.item_cameras_price) TextView price;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            likesButton.setOnClickListener(this);
            commentsButton.setOnClickListener(this);
            ivItem.setOnClickListener(this);
        }

        public void bindItem(Product product) {
            if (product.getPhoto() == null || product.getPhoto().equals("")) {
                ivItem.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_error));
            }

            RequestOptions options = new RequestOptions().error(R.drawable.icon_error);
            Glide
                    .with(mContext)
                    .load(product.getPhoto())
                    .apply(options)
                    .into(ivItem);

            name.setText(product.getName());
            numLikes.setText(String.valueOf(product.getNumLikes()));
            numComments.setText(String.valueOf(product.getNumComments()));
            price.setText(String.valueOf("$" + product.getPrice()));
            if (product.getStatus().equals(Product.STATUS_SOLD_OUT)) {
                ivSoldOut.setVisibility(View.VISIBLE);
            } else {
                ivSoldOut.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_cameras_likes_button:
                    mListener.onFavouriteProductItemClick(mProducts.get(getAdapterPosition()));
                    break;
                case R.id.item_cameras_comments_button:
                    mListener.onCommentsProductItemClick(mProducts.get(getAdapterPosition()));
                    break;
                case R.id.item_cameras_image:
                    mListener.onImageProductItemClick(mProducts.get(getAdapterPosition()));
                    break;
                default:
                    Log.e(TAG,"OnClick event received for an unknown view Id ");
            }
        }
    }
}
