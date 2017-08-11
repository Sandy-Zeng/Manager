package chinasoft.com.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import chinasoft.com.logindemo.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Order2Fragment extends android.app.Fragment {

        private static final String ARG_POSITION = "position";

        private int position;

        private EditText searchbyuid;
        private EditText searchbyoid;
        private EditText searchbytime;
        private ImageView s1;
        private ImageView s2;
        private ImageView s3;
        public static Order2Fragment newInstance(int position) {
            Order2Fragment f = new Order2Fragment();
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v=inflater.inflate(R.layout.fragment_order2,container,false);
            searchbyuid=(EditText)v.findViewById(R.id.searchbyuid);
            searchbyoid=(EditText)v.findViewById(R.id.searchbyoid);
            searchbytime=(EditText)v.findViewById(R.id.searchbytime);
            s1=(ImageView)v.findViewById(R.id.s1);
            s2=(ImageView)v.findViewById(R.id.s2);
            s3=(ImageView)v.findViewById(R.id.s3);
            s1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uid1=searchbyuid.getText().toString();
                    FormBody.Builder builder1=new FormBody.Builder();
                    FormBody formBody=builder1.add("id",uid1)
                            .build();

                    Request.Builder builder2=new Request.Builder();
                    Request request=builder2.url("http://192.168.40.14:8080/dgManager/orderinfo_SearchByUid_android")
                            .post(formBody)
                            .build();
                    exec(request);
                }
            });
            s2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String oid1=searchbyoid.getText().toString();
                    FormBody.Builder builder1=new FormBody.Builder();
                    FormBody formBody=builder1.add("id",oid1)
                            .build();

                    Request.Builder builder2=new Request.Builder();
                    Request request=builder2.url("http://192.168.40.14:8080/dgManager/orderinfo_findById_android")
                            .post(formBody)
                            .build();
                    exec(request);
                }
            });
            s3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String time1=searchbytime.getText().toString();
                    FormBody.Builder builder1=new FormBody.Builder();
                    FormBody formBody=builder1.add("time",time1)
                            .build();

                    Request.Builder builder2=new Request.Builder();
                    Request request=builder2.url("http://192.168.40.14:8080/dgManager/orderinfo_SearchByTime_android")
                            .post(formBody)
                            .build();
                    exec(request);
                }
            });
            return v;
        }

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
