package chinasoft.com.logindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_order_list)
public class OrderListActivity extends Activity {
    @ViewInject(R.id.order_list)
    private ListView order_list;
    @ViewInject(R.id.back)
    private ImageView back;

    private ArrayList<Map<String, Object>> lists = new ArrayList<>();
    private int[] image = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16};
    private List<Integer> pid = new ArrayList<>();
    private List<String> time = new ArrayList<>();
    private List<String> totalMoney = new ArrayList<>();
    private List<Integer> orderID = new ArrayList<>();
    private List<Integer> totalAmount = new ArrayList<>();
    private List<JSONArray> products = new ArrayList<>();
    private int[] orderId={1,2,3};
    private Date[] dateId={};
    private String[] moneyId={"100","200","300"};
    private int[] countId={3,2,1};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_order_list);
        x.view().inject(this);

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");

        JSONArray orderList = JSON.parseArray(json);
        for (int i = 0; i < orderList.size(); i++) {
            com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) orderList.getJSONObject(i);
            time.add((String) o.get("orderTime"));
            totalAmount.add((Integer) o.get("totalAmount"));
            totalMoney.add((String) o.get("totalMoney"));
            orderID.add((Integer) o.get("oId"));
            products.add((JSONArray) o.get("product"));
        }


        for (int i = 0; i < orderList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject) products.get(i).get(0);
            map.put("img", image[(Integer) object.get("pid") - 1]);
            map.put("number", orderID.get(i));
            map.put("date", time.get(i));
            map.put("count", totalAmount.get(i));
            map.put("money", totalMoney.get(i));
            map.put("title", object.get("pname"));
            lists.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(OrderListActivity.this, lists, R.layout.order_item, new String[]{"img", "number", "date", "count", "money", "title"}, new int[]{R.id.order_img, R.id.order_id, R.id.order_time, R.id.count, R.id.money, R.id.product_name});
        order_list.setAdapter(adapter);
        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    }
}
