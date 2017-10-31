package com.awoisoak.market;

import static junit.framework.Assert.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.awoisoak.market.presentation.main.MainActivity;
import com.awoisoak.market.presentation.main.clothesfragment.dagger.DaggerClothesComponent;
import com.awoisoak.market.presentation.main.clothesfragment.impl.ClothesFragment;
import com.awoisoak.market.presentation.main.clothesfragment.impl.ClothesPresenterImpl;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by awo on 9/26/17.
 */
//TODO check all lifecycle calls from view are triggering the corresponding ones in presenter

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,packageName = "com.awoisoak.Market")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClothesViewTest {



    @InjectMocks
    private ClothesFragment mView;

    @Mock
    ClothesPresenterImpl mPresenter;

//    @Mock
//    MarketApplication mMarketApplication;

    MainActivity mActivity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mView = ClothesFragment.newInstance(1);
        startFragment(mView);
        assertNotNull(mView);
//        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();





//        ClothesFragment fragment = Robolectric.buildFragment(ClothesFragment.class).create().get();

//        ClothesFragment fragment = ClothesFragment.newInstance(1);
//        fragment.onStart();

//        SupportFragmentTestUtil.startFragment(fragment,);


         mActivity = Robolectric.setupActivity(MainActivity.class);
        ClothesFragment fragment = new ClothesFragment();
        startFragment(fragment);
//        mMarketApplication = new MarketApplication();
//        mMarketApplication.onCreate();
    }


    @Test
    public void testA_checkLifecycleCallbacks(){
//        mView.onCreate(null);
//        ClothesFragment fragment = ClothesFragment.newInstance(1);
//        fragment.onStart();

        verify(mPresenter).onCreate();

        mView.onResume();
        verify(mPresenter).onResume();

        mView.onDestroy();
        verify(mPresenter).onDestroy();

    }

    @Test
    public void testB_checkXXXX() {
        mView.showToast("hola");
        Toast t = Mockito.spy(Toast.makeText(mock(Context.class), "hola", Toast.LENGTH_LONG));
        verify(t).show();
    }


    private void startFragment( Fragment fragment ) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null );
        fragmentTransaction.commit();
    }

}
