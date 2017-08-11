package chinasoft.com.logindemo;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import chinasoft.com.util.GetDataTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class BlogFragment extends Fragment {
    private List<Map<String,Object>> lists=new ArrayList<>();
    private  LinkedList<String>  titles=new LinkedList<String>();
    private  LinkedList<String>  img=new LinkedList<String>();
    private  LinkedList<String>  date=new LinkedList<String>();
    private  LinkedList<String>  url=new LinkedList<String>();
    private  LinkedList<String>  author=new LinkedList<String>();
    private Dialog dialog;




    private PullToRefreshListView mPullRefreshListView;
    //普通的listview对象
    private ListView actualListView;
    //添加一个链表数组，来存放string数组，这样就可以动态增加string数组中的内容了
    private LinkedList<String> mListItems;
    //给listview添加一个普通的适配器
    private SimpleAdapter mAdapter;
    final LinkedList<Drawable> drawables = new LinkedList<>();


    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==1){
                String result=(String) msg.obj;
                Log.i("info",result);
                com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject) JSON.parse(result);
                    com.alibaba.fastjson.JSONObject resultdata = (com.alibaba.fastjson.JSONObject)object.get("result");
                    JSONArray array =(JSONArray)resultdata.get("data");
                for (int i = 0; i < 5; i++)
                    {
                        com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject)array.getJSONObject(i);
                        Log.i("info",o.getString("title"));
                        titles.add(o.getString("title"));
                        date.add(o.getString("date"));
                        img.add(o.getString("thumbnail_pic_s"));
                        author.add(o.getString("author_name"));
                        url.add(o.getString("url"));
                    }
                    Log.i("info",titles.get(0));
                    Log.i("info",titles.get(1));

                final int[] Ids={R.id.newsImage,R.id.newsTitle,R.id.newsAuthor,R.id.newsDate};
                final String[] keys={"image","title","author","date"};
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0; i < img.size(); i++)

                        {
                            Drawable drawable = loadImageFromNetwork(img.get(i));
                            drawables.add(drawable);
                            Log.i("info", Integer.toString(i));
                        }
                       for(int i=0;i<img.size();i++) {
                            Map<String, Object> map = new HashMap<>();

                            map.put("image", drawables.get(i));
                            map.put("title", titles.get(i));
                            map.put("author", author.get(i));
                            map.put("date", date.get(i));
                            lists.add(map);
                            Log.i("info",Integer.toString(lists.size()));
                        }
                        Log.i("info",Integer.toString(lists.size()));
                        mAdapter=new SimpleAdapter(getActivity().getApplicationContext(),lists,R.layout.refreshlistview_item,keys,Ids);
                        mAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object data, String textRepresentation) {
                                if(view instanceof ImageView & data instanceof Drawable){
                                    ImageView v =(ImageView)view;
                                    v.setImageDrawable((Drawable) data);
                                    return true;

                                }
                                return false;
                            }
                        });
                        mAdapter.notifyDataSetChanged();
                        Message msg = new Message();
                        msg.what = 1;
                        imagehandler.sendMessage(msg);

                    }

                }).start();
            }
        }

    };

    Handler imagehandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    //mAdapter.notifyDataSetChanged();
                    actualListView.setAdapter(mAdapter);
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };


    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }

        return drawable ;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Drawable>
    {

        private List<Drawable> drawables;

        public DownloadImageTask(List<Drawable> drawables) {
            this.drawables = drawables;
        }
        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.blog,null);
        initView(view);

        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        //设置拉动监听器
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(view.getContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                // 开始执行异步任务，传入适配器来进行数据改变
                new GetDataTask(mPullRefreshListView, mAdapter,titles,url,author,img,date,drawables).execute();
            }
        });

        // 添加滑动到底部的监听器
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(view.getContext(), "已经到底了", Toast.LENGTH_SHORT).show();
            }
        });

        //mPullRefreshListView.isScrollingWhileRefreshingEnabled();//看刷新时是否允许滑动
        //在刷新时允许继续滑动
        mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
        //mPullRefreshListView.getMode();//得到模式
        //上下都可以刷新的模式。这里有两个选择：Mode.PULL_FROM_START，Mode.BOTH，PULL_FROM_END
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        //一打开应用就自动刷新，下面语句可以写到刷新按钮里面
        mPullRefreshListView.setRefreshing(true);

        return view;
    }


    private void initView(View v) {
       //initPTRListView(v);
        initListView(v);
        //initPTRListView(v);
    }



    private void initPTRListView(final View view) {
        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        //设置拉动监听器
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(view.getContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                // 开始执行异步任务，传入适配器来进行数据改变
                new GetDataTask(mPullRefreshListView, mAdapter,titles,url,author,img,date,drawables).execute();
            }
        });

        // 添加滑动到底部的监听器
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(view.getContext(), "已经到底了", Toast.LENGTH_SHORT).show();
            }
        });

        //mPullRefreshListView.isScrollingWhileRefreshingEnabled();//看刷新时是否允许滑动
        //在刷新时允许继续滑动
        mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
        //mPullRefreshListView.getMode();//得到模式
        //上下都可以刷新的模式。这里有两个选择：Mode.PULL_FROM_START，Mode.BOTH，PULL_FROM_END
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        /**
         * 设置反馈音效
         */
       // SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
        //soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        //soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        //soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        //mPullRefreshListView.setOnPullEventListener(soundListener);
    }

    /**
     * 设置listview的适配器
     */
    private void initListView(View view) {
        //通过getRefreshableView()来得到一个listview对象
        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);

        actualListView = mPullRefreshListView.getRefreshableView();

        Request request =new Request.Builder().url("http://toutiao-ali.juheapi.com/toutiao/index?type=shishang")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Authorization","APPCODE 2bdfa2f7809242649877231bc77a4185")
                .get()
                .build();
        exec(request);
        dialog = new Dialog(view.getContext());
        View viewKilling = View.inflate(view.getContext(), R.layout.loading_dialog, null);
        dialog.setCancelable(false);
        dialog.setContentView(viewKilling);
        dialog.show();

        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),WebActivity.class);
                intent.putExtra("url",url.get(position));
                startActivity(intent);
            }
        });


    }

    private void exec(Request request){
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info","链接失败");
                Log.i("info", e.toString());
                /*new AlertDialog.Builder(getActivity().getBaseContext()).setTitle("链接失败")
                        .setView(LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.oauthing, null))
                        .create().show();*/
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("info","链接成功");
                String s=response.body().string();
                Message message =new Message();
                message.what=1;
                message.obj =s;
                handler.sendMessage(message);
            }
        });
    }

}