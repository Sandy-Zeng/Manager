package chinasoft.com.logindemo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinasoft.com.chinasoft.com.adapter.MySimpleAdapter;
import chinasoft.com.dbutil.LikeHelper;
import chinasoft.com.util.CycleViewPager;

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

    /**
     * 轮播图
     */
    CycleViewPager mCycleViewPager;
    private int[] image={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,
            R.drawable.p7,R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private List<Integer> pid = new ArrayList<>();
    private String[] title={"薏仁水","sofina妆前乳","DMC欣兰冻膜","肌美精3D面膜","城野医生毛孔收敛水","sana豆乳化妆水","sana豆乳乳液",
            "sana叶绿素美背喷雾","canmake透亮美肌粉饼","5色眼影14色"};
    private String[] place={"日本","日本","台湾","日本","日本","日本","日本","日本","日本","日本"};
    private int[] flags={R.drawable.japan,R.drawable.japan,R.drawable.taiwan,R.drawable.japan,R.drawable.japan,R.drawable.japan,R.drawable.japan,R.drawable.japan,R.drawable.japan,R.drawable.japan};
    private String[] price={"￥75","￥110","￥178","￥65","￥125","￥78","￥78","￥90","￥95","￥89"};
    private int[] stars={5,5,4,5,5,4,4,3,5,5};

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

        grid.setFocusable(false);
        listView.setFocusable(false);

        //listView的列表适配器
        int[] Ids={R.id.img,R.id.title,R.id.place,R.id.price,R.id.stars,R.id.flag,R.id.like};
        String[] keys={"image","title","place","price","stars","flag","like"};
        MySimpleAdapter simpleAdapter=new MySimpleAdapter(v.getContext(),lists,R.layout.item_layout,keys,Ids,pid);
        listView.setAdapter(simpleAdapter);
        LikeHelper likeHelper = new LikeHelper();
        for(int i=0;i<image.length;i++) {
            Map<String, Object> map = new HashMap<>();
            //RatingBar ratingBar = (RatingBar) findViewById(R.id.stars);
            //ratingBar.setNumStars(stars[i]);
            map.put("image", image[i]);
            map.put("title", title[i]);
            map.put("place", place[i]);
            map.put("price", price[i]);
            map.put("stars",stars[i]);
            map.put("flag",flags[i]);

            if(likeHelper.hasLike(pid.get(i)))
                map.put("like",R.drawable.like_press);
            else
                map.put("like",R.drawable.like_normal);
            lists.add(map);
        }


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

        //设置轮播图的数据
        initData();
        initView(v);

        return v;
    }
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
