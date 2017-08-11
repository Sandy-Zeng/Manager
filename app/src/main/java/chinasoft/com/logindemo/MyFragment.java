package chinasoft.com.logindemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class MyFragment extends Fragment {
    private ImageView note;
    private ImageView collection;
    private Fragment contentFragmemt;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ImageView history;
    private ImageView setting;
    private ImageView go_order;
    private TextView track;
    private TextView order;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_my,container,false);
        note=(ImageView)v. findViewById(R.id.note);
        collection=(ImageView)v. findViewById(R.id.collection);
        track= (TextView) v.findViewById(R.id.track);
        go_order= (ImageView) v.findViewById(R.id.go_order);
        order= (TextView) v.findViewById(R.id.order);
        //fragment管理者
        fragmentManager=getFragmentManager();
        transaction=fragmentManager.beginTransaction();
      // Fragment init=new My_Note_Fragment();
        Fragment init=new My_Collection_Fragment();
        transaction.replace(R.id.fragmentPageMy,init,"fragment");//
        transaction.commit();

        //足迹
        history = (ImageView) v.findViewById(R.id.searchHistory);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        //设置
        setting = (ImageView) v.findViewById(R.id.gosetting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConfigActivity.class);
                startActivity(intent);
            }
        });


        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        go_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                String str = sp.getString("username", "");

                Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/orderinfo_SearchByUname_android?name=" + str)
                        .get()
                        .build();

                exec(request);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_order=new Intent(getActivity(),OrderListActivity.class);
                startActivity(go_to_order);
            }
        });

        return v ;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (msg.what == 1) {
                Log.i("info", result);

                Intent go_to_order = new Intent(getActivity(), OrderListActivity.class);
                go_to_order.putExtra("json", result);
                startActivity(go_to_order);

            }
        }

    };


    private void exec(Request request) {
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
                message.what = 1;
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }

}
