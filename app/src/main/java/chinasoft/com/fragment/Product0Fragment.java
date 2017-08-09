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

public class Product0Fragment extends android.app.Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static Product0Fragment newInstance(int position) {
       Product0Fragment f = new Product0Fragment();
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
    private String[]brandname={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
    private int[]goodsimg={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private String[]goodsname={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
    private String[]goodsaddress={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
    private String[]goodsprice={"30","5","6","7","2","1","10","4","1","22"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_my_note,container,false);
        listView=(ListView)v.findViewById(R.id.listviewmynote);
        String []keys={"brandname","goodsimg","goodsname","goodsaddress","goodsprice"};
        int[]ids={R.id.brandname,R.id.goodsimg,R.id.goodsname, R.id.goodsaddress,R.id.goodsprice};

        //构造map
        SimpleAdapter simpleAdapter=new SimpleAdapter(v.getContext(),lists,R.layout.fragment_product0,keys,ids);
        listView.setAdapter(simpleAdapter);

        for(int i=0;i<brandname.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("brandname",brandname[i]);
            map.put("goodsimg",goodsimg[i]);
            map.put("goodsname",goodsname[i]);
            map.put("goodsaddress",goodsaddress[i]);
            map.put("goodsprice",goodsprice[i]);
            lists.add(map);
        }
        return v;
    }
}
