package chinasoft.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinasoft.com.logindemo.R;

public class Order0Fragment extends android.app.Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

  /*  *//*向Java端申请数据*//*
    private TextView textView;
    OkHttpClient okHttpClient=new OkHttpClient();
    Handler handler=new Handler(){//Handler和子线程通过Message对象来传递数据
        @Override
        public void handleMessage(Message msg) {//子类重写此方法接受数据
            super.handleMessage(msg);
            if(msg.what==1){
                String result=(String)msg.obj;
                Log.i("info",result);
                textView.setText(result);
            }
        }
    };*/
    public static Order0Fragment newInstance(int position) {
        Order0Fragment f = new Order0Fragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);

    }
        private ListView listView;
        private List<Map<String,Object>> lists=new ArrayList<>();
        private String[]brandname={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
        private int[]goodsimg={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
                R.drawable.p8,R.drawable.p9,R.drawable.p10};
        private String[]goodsname={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
        private String[]goodssort={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
        private String[]goodsprice={"30","5","6","7","2","1","10","4","1","22"};
        private String[]goodsnum={"7","8","7","7","8","8","7", "9","10","11"};

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v=inflater.inflate(R.layout.fragment_my_note,container,false);
            listView=(ListView)v.findViewById(R.id.listviewmynote);
            String []keys={"brandname","goodsimg","goodsname","goodssort","goodsprice","goodsnum"};
            int[]ids={R.id.brandname,R.id.goodsimg,R.id.goodsname,R.id.goodssort,R.id.goodsprice,R.id.goodsnum};

            //构造map
            SimpleAdapter simpleAdapter=new SimpleAdapter(v.getContext(),lists,R.layout.fragment_order0,keys,ids);
            listView.setAdapter(simpleAdapter);

            for(int i=0;i<brandname.length;i++){
                Map<String,Object>map=new HashMap<>();
                map.put("brandname",brandname[i]);
                map.put("goodsimg",goodsimg[i]);
                map.put("goodsname",goodsname[i]);
                map.put("goodssort",goodssort[i]);
                map.put("goodsprice",goodsprice[i]);
                map.put("goodsnum",goodsnum[i]);
                lists.add(map);
            }
            return v;
       /*  View v=inflater.inflate(R.layout.fragment_note,container,false);
         Log.i("info","request");
            Request request1=new Request.Builder()
                    .url("http://192.168.40.14:8080/dgManager/orderinfo_search").get().build();
            exec(request1);
            return v;*/
    }
  /*  private void exec(Request request){
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("异常","----->"+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("成功","----->");
                String s=response.body().string();
                Message message=new Message();
                message.what=1;
                message.obj=s;
                handler.sendMessage(message);
            }
        });
    }*/
}
