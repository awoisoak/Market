package com.awoisoak.market.presentation.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.awoisoak.market.BuildConfig;
import com.awoisoak.market.R;
import com.awoisoak.market.presentation.main.clothesfragment.impl.ClothesFragment;
import com.awoisoak.market.presentation.main.camerasfragment.impl.CamerasFragment;
import com.awoisoak.market.presentation.main.videogamesfragment.impl.VideogamesFragment;
import com.awoisoak.market.utils.signals.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.tabLayout) TabLayout mTabLayout;

    public static final int CAMERAS_TAB = 0;
    public static final int CLOTHES_TAB = 1;
    public static final int VIDEO_GAMES_TAB = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupSupportActionBar();
        setupViewPager();
        setupTabLayout();
        checkFlavorCompilation();
        setOnPageChangeListener();
    }


    private void setupSupportActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.drawable.icon_launcher);
            actionBar.setTitle(R.string.market);
        }
    }

    private void setupViewPager() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());
        mViewPager.setAdapter(sectionsPagerAdapter);
        mViewPager.setCurrentItem(CLOTHES_TAB, true);
        mViewPager.setOffscreenPageLimit(3);
    }

    private void setupTabLayout() {
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        mTabLayout.setSelectedTabIndicatorHeight(
                (int) (3 * getResources().getDisplayMetrics().density));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.colorAccentClear),
                getResources().getColor(R.color.colorAccent));
        mTabLayout.setupWithViewPager(mViewPager);

    }


    @OnClick(R.id.personalized_fab)
    public void submit(View view) {
        final Activity activity = this;
        Toast.makeText(activity, R.string.sell_feature_not_implemented,
                Toast.LENGTH_SHORT).show();
    }


    /**
     * Production flavor is not implemented (it's out of scope for the test)
     * Display a dialog if a production build is run
     */
    private void checkFlavorCompilation() {
        if (BuildConfig.FLAVOR.equals("prod")) {
            Log.e(TAG, "Production flavor is not implemented, cancelling execution...");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.prod_flavor_not_implemented))
                    .setTitle("Message from developer");

            builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case CAMERAS_TAB:
                    return CamerasFragment.newInstance(position + 1);
                case CLOTHES_TAB:
                    return ClothesFragment.newInstance(position + 1);
                case VIDEO_GAMES_TAB:
                    return VideogamesFragment.newInstance(position + 1);

                default:
                    Log.e(TAG, "Fragment position not expected | position " + position);
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case CAMERAS_TAB:
                    return getString(R.string.cameras);
                case CLOTHES_TAB:
                    return getString(R.string.clothes);
                case VIDEO_GAMES_TAB:
                    return getString(R.string.videogames);
            }
            return null;
        }
    }


    /**
     * Set listener to be able to detect when the different fragments are being displayed to the
     * user in order to trigger the corresponding logic needed.
     */
    private void setOnPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                RxBus.getInstance().send(new VisibleEvent(position));
            }
        });
    }

}
