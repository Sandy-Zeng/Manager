package chinasoft.com.logindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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
import com.alibaba.fastjson.JSONArray;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import chinasoft.com.adapter.SlideFragmentPagerAdapter;
import chinasoft.com.dbutil.HistoryHelper;
import chinasoft.com.service.BackGroundService;
import chinasoft.com.service.Message;
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
    @ViewInject(R.id.webPrice)
    private TextView webPrice;
    @ViewInject(R.id.brand)
    private TextView brand;
    @ViewInject(R.id.storeNum)
    private TextView storeNum;
    @ViewInject(R.id.back)
    private ImageView back;
    @ViewInject(R.id.tablayout)
    private TabLayout tablayout;
    private int[] image = {R.drawable.p1big, R.drawable.p2big, R.drawable.p3big, R.drawable.p4big, R.drawable.p5big, R.drawable.p6big,
            R.drawable.p7big, R.drawable.p8big, R.drawable.p9big, R.drawable.p10big, R.drawable.p11big, R.drawable.p12big, R.drawable.p13big, R.drawable.p14big,
            R.drawable.p15big, R.drawable.p16big};
    private int[][] imageDetail = {{R.drawable.p1a, R.drawable.p1b}, {R.drawable.p2a, R.drawable.p2b}, {R.drawable.p3a, R.drawable.p3b}, {R.drawable.p4a, R.drawable.p4b}, {R.drawable.p5a, R.drawable.p5b},
            {R.drawable.p6a, R.drawable.p6b}, {R.drawable.p7a, R.drawable.p7b}, {R.drawable.p8a, R.drawable.p8b}, {R.drawable.p9a, R.drawable.p9b}, {R.drawable.p10a, R.drawable.p10b}, {R.drawable.p11a, R.drawable.p11b},
            {R.drawable.p12a, R.drawable.p12b}, {R.drawable.p13a, R.drawable.p13b}, {R.drawable.p14a, R.drawable.p14b}, {R.drawable.p15a, R.drawable.p15b}, {R.drawable.p16a, R.drawable.p16b}};


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
        String title = (String) object.get("productName");
        String prices = (String) object.get("price");
        String place = (String) object.get("country");
        String web = (String) object.get("webPrice");
        Integer num = (Integer) object.get("StoreNum");
        String brandName = (String) object.get("brandname");
        String detail = (String) object.get("detailInfo");
        String country = (String) object.get("country");
        String vipPrice = (String) object.get("vipPrice");
        String hotLevel = Integer.toString((Integer) object.get("hotLevel"));
        String type = (String) object.get("type");
        List<Message> messageList = new ArrayList<>();
        JSONArray messages = (JSONArray) object.get("message");
        for (int i = 0; i < messages.size(); i++) {
            com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) messages.getJSONObject(i);
            Message message = new Message((String) o.get("message_username"), (String) o.get("message_content"), (String) o.get("message_time"));
            messageList.add(message);
        }
        pname.setText(title);
        price.setText(prices);
        sellAmount.setText(Integer.toString((Integer) object.get("sellAmount")));
        address.setText((String) object.get("sendAddr"));
        pimage.setImageResource(image[pid - 1]);
        webPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        webPrice.setText(web);
        brand.setText(brandName);
        storeNum.setText(Integer.toString(num));
        tablayout.setTabTextColors(R.color.black, R.color.appColor);

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
            historyHelper.add(pid, username, title, place, prices);
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
        int[] images = new int[2];
        images = imageDetail[pid - 1];
        SlideFragmentPagerAdapter sf = new SlideFragmentPagerAdapter(getSupportFragmentManager(), images);
        sf.set(brandName, country, hotLevel, detail, type);
        sf.setMessage(messageList);
        viewPager.setAdapter(sf);
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
