package chinasoft.com.logindemo;

import android.app.Fragment;
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

public class My_Note_Fragment extends Fragment {

    private ListView listView;
    private List<Map<String,Object>> lists=new ArrayList<>();
   private String[]colnum={"7","8","7","7","8","8","7", "9","10","11"};
    private String[]fannum={"3","5","6","7","2","1","10","4","1","22"};
    private int[]imgId1={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private int[]imgId2={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private int[]imgId3={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private int[]touxiang={R.drawable.tx1,R.drawable.tx2,R.drawable.tx3,R.drawable.tx4,R.drawable.tx5,R.drawable.tx6,R.drawable.tx7,
            R.drawable.tx8,R.drawable.tx9,R.drawable.tx10};

    private String[]name={"小仙女1","小仙女2","小仙女3","小仙女4","小仙女5","小仙女6","小仙女7","小仙女8","小仙女9","小仙女10"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_my_note, container, false);
       // View v1=inflater.inflate(R.layout.note_listview, container, false);
        listView=(ListView)v.findViewById(R.id.listviewmynote);
        String[]keys={"colnum","fannum","imgId1","imgId2","imgId3","touxiang","name"};
        int[]ids={R.id.colnum1,R.id.fannum,R.id.noteimg1,R.id.noteimg2,R.id.noteimg3,R.id.touxiang1,R.id.fanname1};
        //构造map
        SimpleAdapter simpleAdapter=new SimpleAdapter(v.getContext(),lists,R.layout.note_listview,keys,ids);
        listView.setAdapter(simpleAdapter);
        for(int i=0;i<name.length;i++){
            Map<String,Object>map=new HashMap<>();
            map.put("colnum",colnum[i]);
            map.put("fannum",fannum[i]);
            map.put("imgId1",imgId1[i]);
            map.put("imgId2",imgId2[i]);
            map.put("imgId3",imgId3[i]);
            map.put("touxiang",touxiang[i]);
            map.put("name",name[i]);
            lists.add(map);
        }
        return v;
    }
}
