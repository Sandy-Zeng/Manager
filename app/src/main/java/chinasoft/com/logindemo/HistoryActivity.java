package chinasoft.com.logindemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinasoft.com.chinasoft.com.adapter.MyLikeAdapter;
import chinasoft.com.dbutil.HistoryHelper;
import chinasoft.com.vo.History;

@ContentView(R.layout.activity_history)
public class HistoryActivity extends AppCompatActivity {
    private ListView listView;
    private List<Map<String, Object>> lists = new ArrayList<>();
    private int[] image = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16};
    private String[] titles = {"爱丽小屋西柚盘", "爱丽小屋冰激凌盘", "爱丽小屋巧克力", "科颜氏牛油果眼霜", "sofina隔离", "kiko401", "kiko402",
            "kiko403", "kiko404", "kiko405"};
    private String[] money = {"70", "80", "70", "70", "80", "80", "70",
            "90", "100", "110"};
    private List<Integer> pid = new ArrayList<>();//获取商品ID
    private List<String> title = new ArrayList<>();//获取商品名
    private List<String> place = new ArrayList<>();//获取商品国家
    private List<String> prices = new ArrayList<>();
    @ViewInject(R.id.back)
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        x.view().inject(this);
        HistoryHelper historyHelper = new HistoryHelper();
        //根据当前的用户获得其所有浏览历史
        SharedPreferences sp = HistoryActivity.this.getSharedPreferences("user", HistoryActivity.this.MODE_PRIVATE);
        //获取Editor对象
        String username = sp.getString("username", "");
        List<History> histories = historyHelper.findAll(username);
        for (int i = 0; i < histories.size(); i++) {
            pid.add(histories.get(i).getPid());
            prices.add(histories.get(i).getPrice());
            title.add(histories.get(i).getTitle());
            place.add(histories.get(i).getPlace());
        }

        //View v1=inflater.inflate(R.layout.note_listview, container, false);
        listView = (ListView) findViewById(R.id.historyListView);
        String[] keys = {"img", "title", "money"};
        int[] ids = {R.id.item_img, R.id.item_title, R.id.item_money};
        MyLikeAdapter simpleAdapter = new MyLikeAdapter(HistoryActivity.this, lists, R.layout.historylv_item, keys, ids, pid, title, place, prices);
        listView.setAdapter(simpleAdapter);
        //构造map
        for (int i = 0; i < histories.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", image[i]);
            map.put("title", title.get(i));
            map.put("money", prices.get(i));
            lists.add(map);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
