package com.snot.bloatware;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import android.app.ActionBar;
import android.app.FragmentTransaction;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by snot on 6/20/13.
 */

public class TabsFragmentActivity extends FragmentActivity implements ActionBar.TabListener
{
    private final String TAG = this.getClass().getName();

    ViewPager	mViewPager;
    TabsAdapter	ta;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	setContentView(R.layout.fragment_tabs_pager);



        ta = new TabsAdapter(getSupportFragmentManager());

	actionBar = getActionBar();
	final ActionBar factionBar = actionBar;
	// Set up the action bar.
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	// Setup view pager
        mViewPager = (ViewPager)findViewById(R.id.pager);
	mViewPager.setAdapter(ta);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                factionBar.setSelectedNavigationItem(position);
            }
        });

    }

    public void addTab(String title, Fragment f)
    {
        ta.addTab(f);
        actionBar.addTab(actionBar.newTab().setText(title).setTabListener(this));
	ta.notifyDataSetChanged();

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


	private static class TabsAdapter extends FragmentPagerAdapter {
		List<Fragment> fragments = new ArrayList<Fragment>();

		public TabsAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		public void addTab(Fragment fragment)
		{
			fragments.add(fragment);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}
	}
}

