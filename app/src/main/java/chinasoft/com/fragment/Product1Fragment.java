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

public class Product1Fragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    private int position;

    public static Product1Fragment newInstance(int position) {
        Product1Fragment f = new Product1Fragment();
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
  private String[]addidname={"商品名称","品牌","售价","会员价","官网售价","成本","热门指数","发货地",
                                "类型","详细信息"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.goodlistview,container,false);
        listView=(ListView)v.findViewById(R.id.listviewmygoods);
        String[]keys={"addidname"};
        int[]ids={R.id.addidname};
        SimpleAdapter simpleAdapter=new SimpleAdapter(v.getContext(),lists,R.layout.fragment_product1,keys,ids);
        listView.setAdapter(simpleAdapter);

        for(int i=0;i<addidname.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("addidname",addidname[i]);
            lists.add(map);
        }
        return v;
    }
}
