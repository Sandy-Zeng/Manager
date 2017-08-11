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

public class ware1Fragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    private int position;

    private EditText adminid;
    private EditText adminname;
    private EditText productid;
    private EditText productname;
    private EditText wareid;
    private EditText inamount;
    private Button warein;

    public static ware1Fragment newInstance(int position) {
        ware1Fragment f = new ware1Fragment();
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

        View v=inflater.inflate(R.layout.fragment_ware1,container,false);
        adminid=(EditText)v.findViewById(R.id.adminid);
        adminname=(EditText)v.findViewById(R.id.adminname);
        productid=(EditText)v.findViewById(R.id.productid);
        productname=(EditText)v.findViewById(R.id.productname);
        wareid=(EditText)v.findViewById(R.id.wareid);
        inamount=(EditText)v.findViewById(R.id.inamount);
        warein=(Button)v.findViewById(R.id.warein);
       warein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(v.getContext(), "进货成功", Toast.LENGTH_SHORT);
               String  adminid1=adminid.getText().toString();
               String  adminname1=adminname.getText().toString();
               String  productid1=productid.getText().toString();
               String  productname1=productname.getText().toString();
                String wareid1=wareid.getText().toString();
                String inamount1=inamount.getText().toString();
                String str="adminId"+adminid1+"productId"+productid1+"productName"+productname1+"wId"+wareid1+"changeNum"+inamount1;
                FormBody.Builder builder1=new FormBody.Builder();
                FormBody formBody=builder1.add("adminId",adminid1)
                        .add("productId",productid1)
                        .add("productName",productname1)
                        .add("wId",wareid1)
                        .add("changeNum",inamount1).build();

                Request.Builder builder2=new Request.Builder();
                Request request=builder2.url("http://192.168.40.14:8080/dgManager/send_in_android")
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
