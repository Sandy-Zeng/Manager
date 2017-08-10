package chinasoft.com.logindemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_order_list)
public class OrderListActivity extends Activity {
    @ViewInject(R.id.order_list)
    private ListView order_list;

    private ArrayList<Map<String, Object>> lists = new ArrayList<>();
    private int[] nameId = {R.drawable.p1, R.drawable.p2, R.drawable.p3};
    private int[] orderId={1,2,3};
    private Date[] dateId={};
    private String[] moneyId={"100","200","300"};
    private int[] countId={3,2,1};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_order_list);
        x.view().inject(this);
        for (int i = 0; i < nameId.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("img",nameId[i]);
            map.put("number",orderId[i]);
            map.put("date",dateId[i]);
            map.put("count",countId[i]);
            map.put("money",moneyId[i]);
            lists.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(OrderListActivity.this, lists, R.layout.item, new String[]{"img","number","date","count","money"}, new int[]{R.id.order_img,R.id.order_id,R.id.order_time,R.id.count,R.id.money});
        order_list.setAdapter(adapter);
        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
