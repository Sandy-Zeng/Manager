package chinasoft.com.logindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import chinasoft.com.adapter.SlideFragmentPagerAdapter;
import chinasoft.com.dbutil.HistoryHelper;
import chinasoft.com.service.BackGroundService;
import chinasoft.com.view.DragScrollDetailsLayout;

@ContentView(R.layout.activity_fragment_viewpager_sliding)
public class ViewPagerlidingLayoutActivity extends AppCompatActivity {

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.drag_content)
    private DragScrollDetailsLayout mDragScrollDetailsLayout;
    @ViewInject(R.id.flag_tips)
    private TextView mTextView;
    @ViewInject(R.id.pname)
    private TextView pname;
    @ViewInject(R.id.price)
    private TextView price;
    @ViewInject(R.id.sellAmount)
    private TextView sellAmount;
    @ViewInject(R.id.address)
    private TextView address;
    @ViewInject(R.id.product_image)
    private ImageView pimage;
    @ViewInject(R.id.back)
    private ImageView back;
    private int[] image = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_fragment_viewpager_sliding);
        x.view().inject(this);
        Intent intent = new Intent(ViewPagerlidingLayoutActivity.this, BackGroundService.class);
        startService(intent);

        Intent in = getIntent();
        String json = in.getStringExtra("json");
        com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject) JSON.parse(json);
        Integer pid = (Integer) object.get("productId");
        pname.setText((String) object.get("productName"));
        price.setText((String) object.get("price"));
        sellAmount.setText(Integer.toString((Integer) object.get("sellAmount")));
        address.setText((String) object.get("sendAddr"));
        pimage.setImageResource(image[pid - 1]);

        //返回上一页
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //将浏览历史存入数据库
        HistoryHelper historyHelper = new HistoryHelper();
        SharedPreferences sp = ViewPagerlidingLayoutActivity.this.getSharedPreferences("user", MODE_PRIVATE);
        String username = sp.getString("username", "");
        if (!historyHelper.hasHistory(pid, username)) {
            historyHelper.add(pid, username);
        }
        historyHelper.close();


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0, false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new SlideFragmentPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        mDragScrollDetailsLayout.setOnSlideDetailsListener(new DragScrollDetailsLayout.OnSlideFinishListener() {
            @Override
            public void onStatueChanged(DragScrollDetailsLayout.CurrentTargetIndex status) {
                if (status == DragScrollDetailsLayout.CurrentTargetIndex.UPSTAIRS)
                    mTextView.setText("下拉查看商品详情及评价");
                else
                    mTextView.setText("上拉回到顶部");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void contect(View view){
        Intent go_contect=new Intent(ViewPagerlidingLayoutActivity.this,ContectActivity.class);
        startActivity(go_contect);
    }
}
