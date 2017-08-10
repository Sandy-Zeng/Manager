package chinasoft.com.logindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinasoft.com.adapter.MenuListAdapter;
import chinasoft.com.chinasoft.com.adapter.MySimpleAdapter;
import chinasoft.com.dbutil.LikeHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@ContentView(R.layout.activity_drop_sort)
public class DropSortActivity extends Activity {
    @ViewInject(R.id.text)
    private TextView text;
    @ViewInject(R.id.plistView)
    private ListView plistView;

    //菜单标题
    private String headers[] = {"排序", "国家", "品牌"};
    private ListView listView1;
    private ListView listView2;
    private ListView listView3;
    private MenuListAdapter mMenuAdapter1;
    private MenuListAdapter mMenuAdapter2;
    private MenuListAdapter mMenuAdapter3;

    private DropDownMenu mDropDownMenu;

    private String sorts[] = {"全部商品", "热度由高到低", "销量由高到低", "价格由高到低", "价格由低到高"};

    private String countries[] = {"不限", "日本", "美国", "台湾", "韩国"};

    private String brands[] = {"不限", "canmake", "mac","sana","tom ford","城野医生","娥佩兰","欣兰","苏菲娜","高姿"};
    private int[] image = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16};
    private List<String> title = new ArrayList<>();
    private List<String> place = new ArrayList<>();
    private List<String> price = new ArrayList<>();
    private List<Integer> stars = new ArrayList<>();
    private int[] flags = {R.drawable.japan, R.drawable.japan, R.drawable.taiwan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan,
            R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan};

    private List<View> popupViews = new ArrayList<>();
    private List<Integer> pid = new ArrayList<>();
    private List<Map<String, Object>> lists = new ArrayList<>();
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_drop_sort);
        x.view().inject(this);

        initView();

        //获取json数据
        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        com.alibaba.fastjson.JSONArray array = JSON.parseArray(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = (JSONObject) array.get(i);
            pid.add((Integer) o.get("productId"));
            title.add((String) o.get("productName"));
            place.add((String) o.get("country"));
            price.add((String) o.get("price"));
            stars.add((Integer) o.get("hotLevel"));


        }


        //listView的列表适配器
        int[] Ids = {R.id.img, R.id.title, R.id.place, R.id.price, R.id.stars, R.id.flag, R.id.like};
        String[] keys = {"image", "title", "place", "price", "stars", "flag", "like"};
        MySimpleAdapter simpleAdapter = new MySimpleAdapter(DropSortActivity.this, lists, R.layout.item_layout, keys, Ids, pid, title, place, price);
        plistView.setAdapter(simpleAdapter);
        LikeHelper likeHelper = new LikeHelper();
        for (int i = 0; i < title.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            //RatingBar ratingBar = (RatingBar) findViewById(R.id.stars);
            //ratingBar.setNumStars(stars[i]);
            map.put("image", image[pid.get(i) - 1]);
            map.put("title", title.get(i));
            map.put("place", place.get(i));
            map.put("price", "￥" + price.get(i));
            map.put("stars", stars.get(i));
            map.put("flag", flags[i]);

            if (likeHelper.hasLike(pid.get(i)))
                map.put("like", R.drawable.like_press);
            else
                map.put("like", R.drawable.like_normal);
            lists.add(map);
        }
        likeHelper.close();


        plistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_view?json=" + pid.get(position))
                        .get()
                        .build();
                exec(request, 1);
            }
        });
    }


    private void exec(Request request, final int tag) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", "链接失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("info", "链接成功");
                String s = response.body().string();
                Message message = new Message();
                message.what = tag;
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String result = (String) msg.obj;
                //String result="ok";
                Log.i("info", result);
                Intent intent = new Intent(DropSortActivity.this, ViewPagerlidingLayoutActivity.class);
                intent.putExtra("json", result);
                startActivity(intent);
            }
            if (msg.what == 2) {
                String result = (String) msg.obj;
                //String result="ok";
                Log.i("info", result);
                Intent intent = new Intent(DropSortActivity.this, DropSortActivity.class);
                intent.putExtra("json", result);
                startActivity(intent);
            }
        }

    };

    private void initView() {

        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        mDropDownMenu.setListView(plistView);


        //init menu listview

        //这里是每个下拉菜单之后的布局,目前只是简单的listview作为展示
        listView1 = new ListView(DropSortActivity.this);
        listView2 = new ListView(DropSortActivity.this);
        listView3 = new ListView(DropSortActivity.this);

        listView1.setDividerHeight(0);
        listView2.setDividerHeight(0);
        listView3.setDividerHeight(0);

        mMenuAdapter1 = new MenuListAdapter(DropSortActivity.this, Arrays.asList(sorts));
        mMenuAdapter2 = new MenuListAdapter(DropSortActivity.this, Arrays.asList(countries));
        mMenuAdapter3 = new MenuListAdapter(DropSortActivity.this, Arrays.asList(brands));

        listView1.setAdapter(mMenuAdapter1);
        listView2.setAdapter(mMenuAdapter2);
        listView3.setAdapter(mMenuAdapter3);

        popupViews.add(listView1);
        popupViews.add(listView2);
        popupViews.add(listView3);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mDropDownMenu.setTabText(sorts[position]);
                text.setText(sorts[position]);
                mDropDownMenu.closeMenu();
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDropDownMenu.setTabText(countries[position]);
                text.setText(countries[position]);
                mDropDownMenu.closeMenu();
            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDropDownMenu.setTabText(brands[position]);
                text.setText(brands[position]);
                mDropDownMenu.closeMenu();
            }
        });

        //排序
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //获取全部商品
                        Request request0 = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_findAllProduct")
                                .get()
                                .build();
                        exec(request0, 2);
                        break;
                    case 1:
                        //热度从高到低
                        Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_sortByHotLevel?json=" + json)
                                .get()
                                .build();
                        exec(request, 2);
                        break;
                    case 2:
                        //销量从高到低
                        Request request1 = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_sortBySellAmount?json=" + json)
                                .get()
                                .build();
                        exec(request1, 2);
                        break;
                    case 3:
                        //价格从高到低
                        Request request2 = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_sortByPrice_high?json=" + json)
                                .get()
                                .build();
                        exec(request2, 2);
                        break;
                    case 4:
                        //价格从低到高
                        Request request3 = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_sortByPrice_low?json=" + json)
                                .get()
                                .build();
                        exec(request3, 2);
                        break;
                }
            }
        });
        //按照国家获取
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = countries[position];
                if (position != 0) {
                    String country = countries[position];
                    Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_findProductByCountry_android?list=" + json + "&country=" + country)
                            .get()
                            .build();
                    exec(request, 2);
                } else {
                    Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_findAllProduct")
                            .get()
                            .build();
                    exec(request, 2);
                }

            }
        });
        //按照品牌
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = brands[position];
                if (position != 0) {
                    Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_findProductByBrand_android?list=" + json + "&brandName=" + str)
                            .get()
                            .build();
                    exec(request, 2);
                } else {
                    Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_findAllProduct")
                            .get()
                            .build();
                    exec(request, 2);
                }
            }
        });


        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews);

    }
}
