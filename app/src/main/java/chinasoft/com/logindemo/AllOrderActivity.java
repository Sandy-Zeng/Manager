package chinasoft.com.logindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@ContentView(R.layout.activity_all_order)
public class AllOrderActivity extends Activity {
    @ViewInject(R.id.commit_order)
    private ListView commit_order;
    @ViewInject(R.id.back)
    private ImageView back;
    @ViewInject(R.id.commit)
    private Button commit;
    @ViewInject(R.id.total)
    private TextView totalPrice;
    private ArrayList<Map<String, Object>> lists = new ArrayList<>();
    private int[] image = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16};
    private List<Integer> pid = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<String> price = new ArrayList<>();
    private List<Integer> number = new ArrayList<>();
    private Double totalMoney = 0.0;
    private String json;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_all_order);
        x.view().inject(this);
        Intent in = getIntent();
        json = in.getStringExtra("json");
        Log.i("info", json);
        JSONArray array = JSON.parseArray(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = (JSONObject) array.get(i);
            title.add((String) o.get("name"));
            pid.add((Integer) o.get("pid"));
            price.add((String) o.get("price"));
            number.add((Integer) o.get("number"));


        }
        for (int i = 0; i < array.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("img", image[pid.get(i) - 1]);
            map.put("count", Integer.toString(number.get(i)));
            map.put("money", price.get(i));
            map.put("title", title.get(i));
            map.put("total", Integer.toString(number.get(i) * Integer.valueOf(price.get(i))));
            lists.add(map);
            totalMoney += number.get(i) * Double.valueOf(price.get(i));
        }
        totalPrice.setText(Double.toString(totalMoney));
        SimpleAdapter adapter = new SimpleAdapter(AllOrderActivity.this, lists, R.layout.allorder_item, new String[]{"img", "title", "money", "count", "total"}, new int[]{R.id.order_img, R.id.product_name, R.id.price, R.id.count, R.id.money});
        commit_order.setAdapter(adapter);
        commit_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllOrderActivity.this, ContectActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(AllOrderActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (msg.what == 0) {

            }

        }
    };

}
