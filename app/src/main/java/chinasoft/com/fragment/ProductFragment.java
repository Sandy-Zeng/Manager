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

public class ProductFragment extends Fragment {
    private CategoryTabStrip tabs;//导航菜单项
    private ViewPager pager;//导航页
    private MyPagerAdapter adapter;
    private int mChildCount=0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.product_fragment,container,false);

        tabs = (CategoryTabStrip)v.findViewById(R.id.category_stripp);
        pager = (ViewPager)v.findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        // pager.setCurrentItem(0);

        return v;
    }

    public class MyPagerAdapter extends MyFragmentPageAdapter {

        private final List<String> catalogs = new ArrayList<String>();

        public MyPagerAdapter(android.app.FragmentManager fm) {
            super(fm);
            catalogs.add(getString(R.string.category_productlist));
            System.out.println(catalogs.size());
            catalogs.add(getString(R.string.category_addproduct));
            System.out.println(catalogs.size());
            catalogs.add(getString(R.string.category_brand));
            System.out.println(catalogs.size());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return catalogs.get(position);
        }

        @Override
        public int getCount() {
            return catalogs.size();
        }


   /*     @Override
        public void notifyDataSetChanged() {
            mChildCount=getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if(mChildCount>0){
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }*/

        @Override
        public Fragment getItem(int position) {


            switch (position){
//                case 0:
//                    return NewsFragment.newInstance(position);
                case 0:
                    return Product0Fragment.newInstance(position);
                case 1:
                    return Product1Fragment.newInstance(position);
                case 2:
                    return Product2Fragment.newInstance(position);
                default:
                    return Product0Fragment.newInstance(position);
            }

        }
    }
}
