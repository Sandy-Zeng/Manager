package chinasoft.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chinasoft.com.logindemo.R;
import chinasoft.com.util.CategoryTabStrip;
import chinasoft.com.util.MyFragmentPageAdapter;
import chinasoft.com.vo.ViewPager;

/**
 * Created by Ｓａｎｄｙ on 2017/8/5.
 */

public class StorageFragment extends Fragment {
    private CategoryTabStrip tabs;//导航菜单项
    private ViewPager pager;//导航页
    private MyPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.storage_fragment,container,false);

        tabs = (CategoryTabStrip)v.findViewById(R.id.category_strips);
        pager = (ViewPager)v.findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        pager.setCurrentItem(0);


        return v;
    }

    public class MyPagerAdapter extends MyFragmentPageAdapter {

        private final List<String> catalogs = new ArrayList<String>();

        public MyPagerAdapter(android.app.FragmentManager fm) {
            super(fm);
            catalogs.add(getString(R.string.category_storagelist));
            catalogs.add(getString(R.string.category_addstorage));
            catalogs.add(getString(R.string.category_minusstorage));
            catalogs.add(getString(R.string.category_recordlist));
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
        public Fragment getItem(int position)        {
            switch (position){
                case 0:
                    return ware0Fragment.newInstance(position);
                case 1:
                    return ware1Fragment.newInstance(position);
                case 2:
                    return ware2Fragment.newInstance(position);
                case 3:
                    return ware3Fragment.newInstance(position);
            };
            return NewsFragment.newInstance(position);
        }

    }
}
