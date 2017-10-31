package com.awoisoak.market;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.awoisoak.market.data.Product;
import com.awoisoak.market.data.remote.MarketApi;
import com.awoisoak.market.data.remote.impl.responses.ListProductsResponse;
import com.awoisoak.market.domain.interactors.ProductsRequestInteractor;
import com.awoisoak.market.presentation.main.clothesfragment.ClothesView;
import com.awoisoak.market.presentation.main.clothesfragment.impl.ClothesPresenterImpl;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClothesPresenterTest {

    @Mock
    private ClothesView mView;

    @Mock
    private ProductsRequestInteractor mServerInteractor;

    @InjectMocks
    private ClothesPresenterImpl mPresenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    List<Product> mProductList;
    ListProductsResponse mResponse;

    @Before
    public void setUp() throws Exception {
        mPresenter.onCreate();
        mProductList = new ArrayList<>();
        mProductList.add(mock(Product.class));
        mResponse = mock(ListProductsResponse.class);
//        doCallRealMethod().when(mPresenter).onProductsReceived(response);

    }


    @Test
    public void testA_checkIfViewIsReleasedOnDestroy() {
        mPresenter.onDestroy();
        assertNull(mPresenter.getView());
    }

    //This might have to ve tested on the interactor test

//    @Test
//    public void checkIfPresenterRegisterToEventBus() {
//        ArgumentCaptor<ListProductsResponse> captor = ArgumentCaptor
//                .forClass(ListProductsResponse.class);
//        SignalManager sm = mock(SignalManager.class);
//        sm.postEvent(captor);
//        verify(sm).postEvent(captor);// of course, u just called it...
//        SignalManagerFactory.getSignalManager().postEvent(captor);
//        verify(mPresenter, times(1))
//                .onProductsReceived(isA(ListProductsResponse
//                .class));
//    }

    @Test
    public void testB_checkOnBottomReached() {
        mPresenter.onBottomReached();
        verify(mServerInteractor, timeout(1000)).getProducts(eq(MarketApi.CATEGORY_ALL), anyInt());
    }

    @Test
    public void testC_checkOnRetryProductRequest() {
        mPresenter.onRetryProductsRequest();
        verify(mServerInteractor, timeout(1000)).getProducts(eq(MarketApi.CATEGORY_ALL), anyInt());
    }

//How to test it?
    @Test
    public void testD_onProductsReceivedEvent() {

        when(mResponse.getCategory()).thenReturn(MarketApi.CATEGORY_ALL);
        when(mResponse.getList()).thenReturn(mProductList);

        //TODO can not  detect it cause it runs in another thread??
        mPresenter.onProductsReceived(mResponse);

//        verify(mView, timeout(1000)).showToast(anyString());
        verify(mView, timeout(1000)).hideSnackbar();
        verify(mView, timeout(1000)).hideProgressBar();
    }

//    @Test
//    public void TestE_checkLifecycleMethodsReceived(){
//
//    }

}
