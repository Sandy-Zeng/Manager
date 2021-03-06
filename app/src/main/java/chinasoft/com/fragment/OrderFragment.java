package chinasoft.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
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

public class OrderFragment extends Fragment{
    private CategoryTabStrip tabs;//导航菜单项
    private ViewPager pager;//导航页
    private MyPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.order_fragment,container,false);

        tabs = (CategoryTabStrip)v.findViewById(R.id.category_stripo);
        pager = (ViewPager)v.findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

        //pager.setCurrentItem(0);

        return v;
    }

    public class MyPagerAdapter extends MyFragmentPageAdapter {

        private final List<String> catalogs = new ArrayList<String>();

        public MyPagerAdapter(android.app.FragmentManager fm) {
            super(fm);
            catalogs.add(getString(R.string.category_orderlist));
            catalogs.add(getString(R.string.category_addorder));
            catalogs.add(getString(R.string.category_searchorder));
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
            Log.i("info",Integer.toString(position));
             switch (position){
                case 0:
                    return Order0Fragment.newInstance(position);
                case 1:
                    return Order1Fragment.newInstance(position);
                 case 2:
                     return Order2Fragment.newInstance(position);
        }
            return NewsFragment.newInstance(position);
        }

    }
}
