package chinasoft.com.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chinasoft.com.logindemo.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ware0Fragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    private int position;

    private ListView listView;
    private View v;
    private int[]goodsimg={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,R.drawable.p14,R.drawable.p15,R.drawable.p16};

    /*连接后台*/
    private String result;
    OkHttpClient okHttpClient=new OkHttpClient();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                result=(String)msg.obj;
                Log.i("info",result);
                resultJson();
            }
        }
    };
    public static ware0Fragment newInstance(int position) {
        ware0Fragment f = new ware0Fragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);

        Log.i("info","request");
        Request request1=new Request.Builder()
                .url("http://192.168.40.14:8080/dgManager/show_android").get().build();

        exec(request1);
    }
    private void exec(Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("异常","---->"+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("成功","---->");
                String s=response.body().string();
                Message message=new Message();
                message.what=1;
                message.obj=s;
                handler.sendMessage(message);
            }
        });
    }
    /*解析Json数据*/

    private static ArrayList<HashMap<String,Object>> Analysis(String jsonStr) throws JSONException {
        JSONArray jsonArray=null;
        //初始化list数组对象
        ArrayList<HashMap<String,Object>>list=new ArrayList<HashMap<String, Object>>();

        jsonArray=new JSONArray(jsonStr);
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            //初始化map数组对象
            HashMap<String,Object>map=new HashMap<String,Object>();
            map.put("waregoodsname",jsonObject.getString("productName"));
            map.put("wareid",jsonObject.getString("wid"));
            map.put("waregoodsnum",jsonObject.getString("storeNum"));
            map.put("productid",jsonObject.getString("pid"));
            list.add(map);
        }
        return list;
    }
    /*ListView中读取数据*/
    private void resultJson(){
        String []keys={"waregoodsname","wareid","waregoodsnum","waregoodsimg"};
        int[]ids={R.id.waregoodsname, R.id.wareid, R.id.waregoodsnum,R.id.waregoodsimg};
        //构造map
        List<HashMap<String,Object>> lists=null;
        try {
            lists=Analysis(result);//解析出json数据
        }catch (Exception e){
            e.printStackTrace();
        }
        List<HashMap<String,Object>>data=new ArrayList<HashMap<String, Object>>();
        for(HashMap<String,Object>news:lists){
            HashMap<String,Object>item=new HashMap<String,Object>();
            item.put("waregoodsname",news.get("waregoodsname"));
            item.put("wareid",news.get("wareid"));
            item.put("waregoodsnum",news.get("waregoodsnum"));
            item.put("waregoodsimg",goodsimg[Integer.valueOf((String)news.get("productid"))-1]);

            Log.i("waregoodsname",(String)news.get("waregoodsname"));
            Log.i("wareid",(String)news.get("wareid"));
            Log.i("waregoodsnum",(String)news.get("waregoodsnum"));
            Log.i("productid",(String)news.get("productid"));
            data.add(item);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(v.getContext(),data,R.layout.fragment_ware0,keys,ids);
        listView.setAdapter(simpleAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_my_note,container,false);
        listView=(ListView)v.findViewById(R.id.listviewmynote);
        return v;
    }
}
