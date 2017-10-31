package com.awoisoak.market.data.remote;

import android.content.res.AssetManager;
import android.util.Log;

import com.awoisoak.market.data.Product;
import com.awoisoak.market.data.remote.impl.responses.ErrorResponse;
import com.awoisoak.market.data.remote.impl.responses.ListProductsResponse;
import com.awoisoak.market.data.remote.impl.responses.MarketResponse;
import com.awoisoak.market.presentation.MarketApplication;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Mock Market Manager
 * This class simulates the connection against the Market backend to retrieve the products
 */

public class MockMarketManager implements MarketApi {
    private static final String TAG = MockMarketManager.class.getSimpleName();

    static MockMarketManager mInstance;

    private static final String CAMERAS_FILE_NAME = "cameras.json";
    private static final String VIDEO_GAMES_FILE_NAME = "videogames.json";
    private static final String CLOTHES_FILE_NAME = "clothes.json";

    private static String mJsonCameras;
    private static String mJsonVideogames;
    private static String mJsonClothesProducts;

    private static List<Product> mCamerasList;
    private static List<Product> mVideogamesList;
    private static List<Product> mClothesList;
    private static int STATUS_OK = 200;
    private static final String ARRAY_NAME = "data";


    public static MockMarketManager getInstance() {
        if (mInstance == null) {
            mInstance = new MockMarketManager();
            getFilesContent();
            parseProducts();
        }
        return mInstance;
    }


    /**
     * Method to read all json files provided
     */
    private static String getFilesContent() {
        String content = "";
        try {
            //cameras.json
            AssetManager am = MarketApplication.getMarketApplication().getAssets();
            InputStream stream = am.open(CAMERAS_FILE_NAME);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            mJsonCameras = new String(buffer);

            //videogames.json
            stream = am.open(VIDEO_GAMES_FILE_NAME);
            size = stream.available();
            buffer = new byte[size];
            stream.read(buffer);
            mJsonVideogames = new String(buffer);

            //clothes.json
            stream = am.open(CLOTHES_FILE_NAME);
            size = stream.available();
            buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            mJsonClothesProducts = new String(buffer);


        } catch (IOException e) {
            Log.e(TAG, "Couldn't read the file");
        }
        return content;
    }

    /**
     * Method to parse the JSON files provided into lists of <{@link Product}>
     */
    private static void parseProducts() {
        //Men
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(mJsonCameras).getAsJsonObject();
        JsonArray jsonArray = json.getAsJsonArray(ARRAY_NAME);
        Gson gson = new Gson();
        mCamerasList = gson.fromJson(jsonArray, new TypeToken<List<Product>>() {
        }.getType());

        //Women
        json = parser.parse(mJsonVideogames).getAsJsonObject();
        jsonArray = json.getAsJsonArray(ARRAY_NAME);
        gson = new Gson();
        mVideogamesList = gson.fromJson(jsonArray, new TypeToken<List<Product>>() {
        }.getType());

        //All
        json = parser.parse(mJsonClothesProducts).getAsJsonObject();
        jsonArray = json.getAsJsonArray(ARRAY_NAME);
        gson = new Gson();
        mClothesList = gson.fromJson(jsonArray, new TypeToken<List<Product>>() {
        }.getType());
    }

    @Override
    public Observable<MarketResponse> request(String category, int offset) {


        return Observable.create((ObservableEmitter<MarketResponse> emitter) ->{

            Log.d(TAG,
                    " MockMarketManager | request | category = " + category + " | offset = " + offset);
            emulateDelay();
            List<Product> productsList = new ArrayList<>();
            switch (category) {
                case MarketApi.CATEGORY_MEN:
                    productsList = mCamerasList;
                    break;
                case MarketApi.CATEGORY_WOMEN:
                    productsList = mVideogamesList;
                    break;
                case MarketApi.CATEGORY_ALL:
                    productsList = mClothesList;
                    break;
                default:
                    ErrorResponse error = new ErrorResponse(category, "Unknown product category");
                    emitter.onError(new Throwable(error.getMessage()));
            }


            ListProductsResponse r;
            if (offset < productsList.size()) {
                if (offset + MAX_NUMBER_PRODUCTS_RETURNED < productsList.size()) {
                    r = new ListProductsResponse(category,
                            productsList.subList(offset, offset + MAX_NUMBER_PRODUCTS_RETURNED));
                } else {
                    r = new ListProductsResponse(category,
                            productsList.subList(offset, productsList.size()));
                }

                r.setCode(STATUS_OK);
                r.setTotalRecords(productsList.size());
                emitter.onNext(r);
            } else {
                List<Product> emptyList = new ArrayList<>();
                r = new ListProductsResponse(category, emptyList);
                r.setCode(STATUS_OK);
                r.setTotalRecords(0);
                emitter.onNext(r);
            }

        });
    }

    /**
     * Emulates some server request delay
     */
    private void emulateDelay() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
