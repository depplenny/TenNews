package com.depplenny.tennews;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 5;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0
                return MyFragment.newInstance(
                        "https://content.guardianapis.com/search?show-fields=body,thumbnail&order-by=newest&section=technology&api-key=test");
            case 1: // Fragment # 1
                return MyFragment.newInstance(
                        "https://content.guardianapis.com/search?show-fields=body,thumbnail&order-by=newest&section=politics&api-key=test");
            case 2: // Fragment # 2
                return MyFragment.newInstance(
                        "https://content.guardianapis.com/search?show-fields=body,thumbnail&order-by=newest&section=business&api-key=test");
            case 3: // Fragment # 3
                return MyFragment.newInstance(
                        "https://content.guardianapis.com/search?show-fields=body,thumbnail&order-by=newest&section=world&api-key=test");
            case 4: // Fragment # 4
                return MyFragment.newInstance(
                        "https://content.guardianapis.com/search?show-fields=body,thumbnail&order-by=newest&section=books&api-key=test");
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle;
        switch (position) {
            case 0: // Fragment # 0
                return "Technology";
            case 1: // Fragment # 1
                return "Politics";
            case 2: // Fragment # 2
                return "Business";
            case 3:
                return "World";
            case 4:
                return "Books";
            default:
                return null;
        }
    }



}
