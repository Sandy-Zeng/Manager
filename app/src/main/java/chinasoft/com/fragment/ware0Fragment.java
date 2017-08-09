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

public class ware0Fragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    private int position;

    public static ware0Fragment newInstance(int position) {
        ware0Fragment f = new ware0Fragment();
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
    private int[]waregoodsimg={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private String[]waregoodsname={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
    private String[]orderid={"1","1","2","2","4","2","5",
            "2","3","1"};
    private String[]wareid={"1","1","2","2","4","2","5",
            "2","3","1"};
    private String[]waregoodsnum={"30","5","6","7","2","1","10","4","1","22"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_my_note,container,false);
        listView=(ListView)v.findViewById(R.id.listviewmynote);
        String []keys={"waregoodsimg","waregoodsname","orderid","wareid","waregoodsnum"};
        int[]ids={R.id.waregoodsimg,R.id.waregoodsname, R.id.orderid, R.id.wareid,R.id. waregoodsnum};

        //构造map
        SimpleAdapter simpleAdapter=new SimpleAdapter(v.getContext(),lists,R.layout.fragment_ware0,keys,ids);
        listView.setAdapter(simpleAdapter);

        for(int i=0;i<waregoodsimg.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("waregoodsimg",waregoodsimg[i]);
            map.put("waregoodsname",waregoodsname[i]);
            map.put("orderid",orderid[i]);
            map.put("wareid",wareid[i]);
            map.put("waregoodsnum",waregoodsnum[i]);
            lists.add(map);
        }
        return v;
    }
}
