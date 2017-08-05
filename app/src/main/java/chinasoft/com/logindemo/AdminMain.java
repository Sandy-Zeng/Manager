package chinasoft.com.logindemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chinasoft.com.chinasoft.com.adapter.MenuAdapter;
import chinasoft.com.fragment.NewsFragment;
import chinasoft.com.util.CategoryTabStrip;
import chinasoft.com.util.SlidingMenu;

public class AdminMain extends AppCompatActivity {

    private SlidingMenu slideMenu1;
    private ListView lv_menu;
    private MenuAdapter menuAdapter;
    private ImageView iv_menu;

    private CategoryTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        initView();

        tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
        pager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabs.setViewPager(pager);

    }

    private void initView() {
        slideMenu1 = (SlidingMenu) findViewById(R.id.slideMenu1);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        menuAdapter = new MenuAdapter(this);
        lv_menu.setAdapter(menuAdapter);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu1.toggle();
            }
        });
        slideMenu1.setOnStatusListener(new SlidingMenu.OnStatusListener() {

            @Override
            public void statusChanged(SlidingMenu.Status status) {
                if (status == SlidingMenu.Status.Open) {
                    Toast.makeText(AdminMain.this, "Open", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminMain.this, "Close", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<String> catalogs = new ArrayList<String>();

        public MyPagerAdapter(FragmentManager fm) {
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
        public android.support.v4.app.Fragment getItem(int position) {
            return NewsFragment.newInstance(position);
        }

    }
}
