package chinasoft.com.util;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import chinasoft.com.logindemo.R;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;


/**
 * Created by surface on 2017/8/8.
 */

public class GetDataTask extends AsyncTask<Void,Void,Void> {
    private PullToRefreshListView mPullRefreshListView;
    private SimpleAdapter mAdapter;
    private LinkedList<String> mListItems;
    private  LinkedList<String>  titles;
    private  LinkedList<String>  img;
    private  LinkedList<String>  date;
    private  LinkedList<String>  url;
    private  LinkedList<String>  author;
    private  LinkedList<Drawable> drawables;


    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String result = (String) msg.obj;
                Log.i("info", result);
                com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject) JSON.parse(result);
                com.alibaba.fastjson.JSONObject resultdata = (com.alibaba.fastjson.JSONObject) object.get("result");
                JSONArray array = (JSONArray) resultdata.get("data");
                for (int i = 0; i < 5; i++) {
                    com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) array.getJSONObject(i);
                    Log.i("info", o.getString("title"));
                    titles.addFirst(o.getString("title"));
                    date.addFirst(o.getString("date"));
                    img.addFirst(o.getString("thumbnail_pic_s"));
                    author.addFirst(o.getString("author_name"));
                    url.addFirst(o.getString("url"));
                }
                Log.i("info", titles.get(0));
                Log.i("info", titles.get(1));

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0; i < img.size(); i++)

                        {
                            Drawable drawable = loadImageFromNetwork(img.get(i));
                            drawables.add(drawable);
                            Log.i("info", Integer.toString(i));
                        }

                        mAdapter.notifyDataSetChanged();
                        Message msg = new Message();
                        msg.what = 1;
                       // imagehandler.sendMessage(msg);

                    }

                }).start();
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


         class DownloadImageTask extends AsyncTask<String, Void, Drawable>
        {

            private List<Drawable> drawables;

            public DownloadImageTask(List<Drawable> drawables) {
                this.drawables = drawables;
            }
            protected Drawable doInBackground(String... urls) {
                return loadImageFromNetwork(urls[0]);
            }

        }


        public GetDataTask(PullToRefreshListView listView,
                       SimpleAdapter adapter, LinkedList<String> titleItems,
                       LinkedList<String> dateItems, LinkedList<String> authorItems,
                       LinkedList<String> imgItems, LinkedList<String> urlItems,
                       LinkedList<Drawable> drawable) {
        // TODO 自动生成的构造函数存根
        mPullRefreshListView = listView;
        mAdapter = adapter;
       // mListItems = listItems;
        titles=titleItems;
        author = authorItems ;
        url = urlItems;
        img= imgItems;
        date = dateItems;
        drawables = drawable;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //模拟请求
            Request request =new Request.Builder().url("http://toutiao-ali.juheapi.com/toutiao/index?type=shishang")
                    .header("User-Agent", "OkHttp Headers.java")
                    .addHeader("Authorization","APPCODE 2bdfa2f7809242649877231bc77a4185")
                    .get()
                    .build();
            exec(request,1);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO 自动生成的方法存根
        super.onPostExecute(result);
        //得到当前的模式
        PullToRefreshBase.Mode mode = mPullRefreshListView.getCurrentMode();
        if(mode == PullToRefreshBase.Mode.PULL_FROM_START) {

        }
        else {

        }
        // 通知数据改变了
        mAdapter.notifyDataSetChanged();
        // 加载完成后停止刷新
        mPullRefreshListView.onRefreshComplete();

    }
    private void exec(Request request,final int tag){
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info","链接失败");
                /*Message message =new Message();
                message.what=1;
                message.obj ="ok";
                handler.sendMessage(message);*/
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("info","链接成功");
                String s=response.body().string();
                Message message =new Message();
                if(tag==1) {
                    message.what = 1;
                }
                if(tag==2){
                    message.what=2;
                }
                message.obj =s;
                handler.sendMessage(message);
            }
        });
    }
}
