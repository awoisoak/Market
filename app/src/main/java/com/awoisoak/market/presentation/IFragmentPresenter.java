package com.awoisoak.market.presentation;


/**
 * Specific lifecycle methods for a Fragment presenter
 */

public interface IFragmentPresenter extends IPresenter {

    void onAttach();

    void onCreateView();

    void onActivityCreated();

    void onDestroyView();

    void onDetach();
}
