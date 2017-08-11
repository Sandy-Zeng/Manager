package chinasoft.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import chinasoft.com.vo.ViewPager;
import chinasoft.com.logindemo.R;
import chinasoft.com.util.CategoryTabStrip;
import chinasoft.com.util.MyFragmentPageAdapter;

/**
 * Created by Ｓａｎｄｙ on 2017/8/5.
 */

public class CustomerFragment extends android.app.Fragment {

    private CategoryTabStrip tabs;//导航菜单项
    private ViewPager pager;//导航页
    private MyPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.customer_fragment,container,false);

        tabs = (CategoryTabStrip)v.findViewById(R.id.category_stripc);
        pager = (ViewPager)v.findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        //pager.setCurrentItem(1);


        return v;
    }

    public class MyPagerAdapter extends MyFragmentPageAdapter {

        private final List<String> catalogs = new ArrayList<String>();

        public MyPagerAdapter(android.app.FragmentManager fm) {
            super(fm);
            catalogs.add(getString(R.string.category_allcustomer));
            catalogs.add(getString(R.string.category_addcustomer));
            catalogs.add(getString(R.string.category_vipcustomer));
            catalogs.add(getString(R.string.category_novipcustomer));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return catalogs.get(position);
        }

        @Override
        public int getCount() {
            return catalogs.size();
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 1:
                    return AllCustomerFragment.newInstance(0);
                case 3:
                    return AllCustomerFragment.newInstance(2);
                case 4:
                    return AllCustomerFragment.newInstance(3);
                default:
                    return NewsFragment.newInstance(position);
            }
        }

    }
}
