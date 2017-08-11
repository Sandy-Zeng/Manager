package chinasoft.com.logindemo;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinasoft.com.chinasoft.com.adapter.MySimpleAdapter;
import chinasoft.com.dbutil.LikeHelper;
import chinasoft.com.util.CycleViewPager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class ShouyeFragment extends Fragment{
    private List<Map<String,Object>> lists=new ArrayList<>();
    private ListView listView;
    private GridView grid;
    private EditText editText;
    private ImageView  imageView;
    int mList[]=new int[4];
    private String json;
    private List<String> title = new ArrayList<>();//获取商品名
    private List<String> place = new ArrayList<>();//获取商品国家
    private List<String> prices = new ArrayList<>();
    private List<Integer> stars = new ArrayList<>();

    /**
     * 轮播图
     */
    CycleViewPager mCycleViewPager;
    private int[] image = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16};
    private List<Integer> pid = new ArrayList<>();
    private int[] flags = {R.drawable.japan, R.drawable.japan, R.drawable.taiwan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan,
            R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan, R.drawable.japan};

    /* private String[] title={"薏仁水","sofina妆前乳","DMC欣兰冻膜","肌美精3D面膜","城野医生毛孔收敛水","sana豆乳化妆水","sana豆乳乳液",
            "sana叶绿素美背喷雾","canmake透亮美肌粉饼","5色眼影14色"};
    private String[] place={"日本","日本","台湾","日本","日本","日本","日本","日本","日本","日本"};*/
   /* private int[] flags={R.drawable.japan,R.drawable.japan,R.drawable.taiwan,R.drawable.japan,R.drawable.japan,R.drawable.japan,R.drawable.japan,R.drawable.japan,R.drawable.japan,R.drawable.japan};*/
 /*   private String[] price={"￥75","￥110","￥178","￥65","￥125","￥78","￥78","￥90","￥95","￥89"};
    private int[] stars={5,5,4,5,5,4,4,3,5,5};*/

    private int[] items={R.drawable.mianshuang,R.drawable.mianmo,R.drawable.jiemian,R.drawable.hufutaozhuang,R.drawable.kouhong,R.drawable.fenbing,R.drawable.yanzhuang,R.drawable.other};
    private String[] name={"面霜","面膜","洁面","护肤套装","唇妆","底妆","眼妆","更多分类"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.fragment_shouye,container,false);
        listView=(ListView)v.findViewById(R.id.listView);//热门商品列表
        grid=(GridView)v.findViewById(R.id.grid);//分类列表
        editText=(EditText)v.findViewById(R.id.gosearch);//搜索框

        pid.add(1);
        pid.add(2);
        pid.add(3);pid.add(4);pid.add(5);pid.add(6);pid.add(7);pid.add(8);pid.add(9);pid.add(10);

        Intent intent = getActivity().getIntent();
        json = intent.getStringExtra("json");
        com.alibaba.fastjson.JSONArray array = JSON.parseArray(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = (JSONObject) array.get(i);
            pid.add((Integer) o.get("productId"));
            title.add((String) o.get("productName"));
            place.add((String) o.get("country"));
            prices.add((String) o.get("price"));
            stars.add((Integer) o.get("hotLevel"));
        }


        grid.setFocusable(false);
        listView.setFocusable(false);

        //listView的列表适配器
        int[] Ids = {R.id.img, R.id.title, R.id.place, R.id.price, R.id.stars, R.id.flag, R.id.like};
        String[] keys={"image","title","place","price","stars","flag","like"};
        MySimpleAdapter simpleAdapter = new MySimpleAdapter(v.getContext(), lists, R.layout.item_layout, keys, Ids, pid, title, place, prices);
        listView.setAdapter(simpleAdapter);
        LikeHelper likeHelper = new LikeHelper();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            //RatingBar ratingBar = (RatingBar) findViewById(R.id.stars);
            //ratingBar.setNumStars(stars[i]);
            map.put("image", image[pid.get(i) - 1]);
            map.put("title", title.get(i));
            map.put("place", place.get(i));
            map.put("price", "￥" + prices.get(i));
            map.put("stars", stars.get(i));
            map.put("flag", flags[pid.get(i) - 1]);

            if(likeHelper.hasLike(pid.get(i)))
                map.put("like",R.drawable.like_press);
            else
                map.put("like",R.drawable.like_normal);
            lists.add(map);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_view?json=" + pid.get(position))
                        .get()
                        .build();
                exec(request, 1);
            }
        });


        //gridView的列表适配器
        class MyAdapter extends BaseAdapter {
            private Context mcontext;
            MyAdapter(Context context){
                mcontext=context;
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public Object getItem(int position) {
                return items[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=LayoutInflater.from(mcontext);
                View view=inflater.inflate(R.layout.grid_item,null);
                ImageView img=(ImageView)view.findViewById(R.id.grid_img);
                TextView titleView=(TextView)view.findViewById(R.id.grid_title);
                img.setImageResource(items[position]);
                titleView.setText(name[position]);
                return view;
            }
        }

        MyAdapter myAdapter=new MyAdapter(v.getContext());
        grid.setAdapter(myAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = name[position];
                Log.i("info", str);
                Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_findAllProductByType?type=" + str)
                        .get()
                        .build();
                exec(request, 2);
            }
        });

        //设置轮播图的数据
        initData();
        initView(v);

        return v;
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
                Log.i("info", s);
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
                Intent intent = new Intent(getActivity(), ViewPagerlidingLayoutActivity.class);
                intent.putExtra("json", result);
                startActivity(intent);
            }
            if (msg.what == 2) {
                String result = (String) msg.obj;
                //String result="ok";
                Log.i("info", result);
                Intent intent = new Intent(getActivity(), DropSortActivity.class);
                intent.putExtra("json", result);
                startActivity(intent);
            }
        }

    };
    private void initData() {
        mList[0]=R.drawable.lunbo1;
        mList[1]=R.drawable.lunbo2;
        mList[2]=R.drawable.lunbo3;
        mList[3]=R.drawable.lunbo4;

    }

    /**
     * 初始化View
     */

    private void initView(View v) {
        mCycleViewPager = (CycleViewPager)v. findViewById(R.id.cycle_view);
        //设置选中和未选中时的图片
        assert mCycleViewPager != null;
        mCycleViewPager.setIndicators(R.mipmap.ad_select, R.mipmap.ad_unselect);
        //设置轮播间隔时间，默认为4000
        mCycleViewPager.setDelay(2000);
        mCycleViewPager.setData(mList, mAdCycleViewListener);
    }

    /**
     * 轮播图点击监听
     */
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(int info, int position, View imageView) {

            if (mCycleViewPager.isCycle()) {
                position = position - 1;
            }
            //Toast.makeText(v.getContext(),  "选择了--" + position, Toast.LENGTH_LONG).show();
            Log.i("info","点击了"+position);
        }
    };

}
