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

public class ware3Fragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";
    private ListView listView;
    private View v;
    private int position;

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
    public static ware3Fragment newInstance(int position) {
        ware3Fragment f = new ware3Fragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
        Log.i("info","position"+position);
        Request request1=new Request.Builder()
                .url("http://192.168.40.14:8080/dgManager/showrecord_android").get().build();
        exec(request1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_my_note,container,false);
        listView=(ListView)v.findViewById(R.id.listviewmynote);
        return v;
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
            map.put("wareopid",jsonObject.getString("id"));
            map.put("operation",jsonObject.getString("operationType"));
            map.put("optime",jsonObject.getString("operationTime"));
            map.put("opamount",jsonObject.getString("changeNumber"));
            map.put("adminopid",jsonObject.getString("adminId"));
            map.put("goodsopid",jsonObject.getString("productId"));
            map.put("opwareid",jsonObject.getString("wId"));
            list.add(map);
        }
        return list;
    }
    /*ListView中读取数据*/
    private void resultJson(){
        String []keys={"wareopid","operation","optime","opamount","adminopid","goodsopid","opwareid"};
        int[]ids={R.id.wareopid, R.id.operation,R.id.optime, R.id.opamount,R.id.adminopid, R.id.goodsopid,R.id.opwareid};
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
            item.put("wareopid",news.get("wareopid"));
            item.put("operation",news.get("operation"));
            item.put("optime",news.get("optime"));
            item.put("opamount",news.get("opamount"));
            item.put("adminopid",news.get("adminopid"));
            item.put("goodsopid",news.get("goodsopid"));
            item.put("opwareid",news.get("opwareid"));
            Log.i("wareopid",(String)news.get("wareopid"));
            Log.i("operation",(String)news.get("operation"));
            Log.i("optime",(String)news.get("optime"));
            Log.i("opamount",(String)news.get("opamount"));
            Log.i("adminopid",(String)news.get("adminopid"));
            Log.i("goodsopid",(String)news.get("goodsopid"));
            Log.i("opwareid",(String)news.get("opwareid"));
            data.add(item);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(v.getContext(),data,R.layout.fragment_ware3,keys,ids);
        listView.setAdapter(simpleAdapter);
    }
}
