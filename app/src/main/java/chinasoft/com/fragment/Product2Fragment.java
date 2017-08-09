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

public class Product2Fragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    private int position;

    public static Product2Fragment newInstance(int position) {
        Product2Fragment f = new Product2Fragment();
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
    private String[]brandname1={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
    private int[]brandimg={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private String[]brandcountry={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_my_note,container,false);
        listView=(ListView)v.findViewById(R.id.listviewmynote);
        String []keys={"brandname1","brandimg","brandcountry"};
        int[]ids={R.id.brandname1, R.id.brandimg, R.id.brandcountry};

        //构造map
        SimpleAdapter simpleAdapter=new SimpleAdapter(v.getContext(),lists,R.layout.fragment_product2,keys,ids);
        listView.setAdapter(simpleAdapter);

        for(int i=0;i<brandname1.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("brandname1",brandname1[i]);
            map.put("brandimg",brandimg[i]);
            map.put("brandcountry",brandcountry[i]);
            lists.add(map);
        }
        return v;
    }
}
