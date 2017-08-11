package chinasoft.com.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import chinasoft.com.logindemo.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Product1Fragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";
    private EditText pnameid;
    private EditText bnameid;
    private EditText countryid;
    private EditText sendAddrid;
    private EditText priceid;
    private EditText vipPriceid;
    private EditText detailInfoid;
    private EditText typeid;
    private EditText primecostid;
    private EditText webPriceid;
    private EditText hotLevelid;

    private int position;
    private Button commit;

    /*连接后台*/
    private String result;
    OkHttpClient okHttpClient=new OkHttpClient();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                String  result=(String)msg.obj;
                Log.i("info",result);
            }
        }
    };

    public static Product1Fragment newInstance(int position) {
        Product1Fragment f = new Product1Fragment();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_product1,container,false);
        pnameid=(EditText)v.findViewById(R.id.pnameid);
        bnameid=(EditText)v.findViewById(R.id.bnameid);
        countryid=(EditText)v.findViewById(R.id.countryid);
        sendAddrid=(EditText)v.findViewById(R.id.sendAddrid);
        priceid=(EditText)v.findViewById(R.id.priceid);
        vipPriceid=(EditText)v.findViewById(R.id.vipPriceid);
        detailInfoid=(EditText)v.findViewById(R.id.detailInfoid);
        typeid=(EditText)v.findViewById(R.id.typeid);
        primecostid=(EditText)v.findViewById(R.id.primecostid);
        webPriceid=(EditText)v.findViewById(R.id.webPriceid);
        hotLevelid=(EditText)v.findViewById(R.id.hotLevelid);
        commit=(Button)v.findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(v.getContext(), "添加成功", Toast.LENGTH_SHORT);
                String  pid=pnameid.getText().toString();
                String  bid=bnameid.getText().toString();
                String  cid=countryid.getText().toString();
                String  sid=sendAddrid.getText().toString();
                String pid1=priceid.getText().toString();
                String vid=vipPriceid.getText().toString();
                String  did=detailInfoid.getText().toString();
                String  tid=typeid.getText().toString();
                String  pid2=primecostid.getText().toString();
                String wid=webPriceid.getText().toString();
                String hid=hotLevelid.getText().toString();
                String str="panme"+pid+"bname"+bid+"country"+cid+"sendAddr"+sid+"price"+pid1+"vipPrice"+vid+"detailInfo"+did+"type"+tid+"primecost"+pid2+"webPrice"+wid+"hotLevel"+hid;
                FormBody.Builder builder1=new FormBody.Builder();
                FormBody formBody=builder1.add("pname",pid)
                        .add("bname",bid)
                        .add("country",cid)
                        .add("sendAddr",sid)
                        .add("price",pid1)
                        .add("vipPrice",vid)
                        .add("detailInfo",did)
                        .add("type",tid)
                        .add("primecost",pid2)
                        .add("webPrice",wid)
                        .add("hotLevel",hid)
                        .build();

                Request.Builder builder2=new Request.Builder();
                Request request=builder2.url("http://192.168.40.14:8080/dgManager/Product_add_android")
                        .post(formBody)
                        .build();
                exec(request);

            }
        });
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


}
