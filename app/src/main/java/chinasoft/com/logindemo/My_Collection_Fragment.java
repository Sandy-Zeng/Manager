package chinasoft.com.logindemo;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinasoft.com.chinasoft.com.adapter.MyLikeAdapter;
import chinasoft.com.dbutil.LikeHelper;
import chinasoft.com.vo.Like;

public class My_Collection_Fragment extends Fragment {

    private ListView listView;
    private List<Map<String,Object>> lists=new ArrayList<>();
    private int[]imgIds={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private String[]titles={"爱丽小屋西柚盘","爱丽小屋冰激凌盘","爱丽小屋巧克力","科颜氏牛油果眼霜","sofina隔离","kiko401","kiko402",
            "kiko403","kiko404","kiko405"};
    private String[]money={"70","80","70","70","80","80","70",
            "90","100","110"};
    private List<Integer> pid = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_my_collection, container, false);


        LikeHelper likeHelper = new LikeHelper();
        SharedPreferences sp = v.getContext().getSharedPreferences("user", v.getContext().MODE_PRIVATE);
        //获取Editor对象
        String username = sp.getString("username", "");
        List<Like> likes = likeHelper.findAll(username);
        for(int i=0;i<likes.size();i++)
        {
            pid.add(likes.get(i).getPid());
        }

        //View v1=inflater.inflate(R.layout.note_listview, container, false);
        listView=(ListView)v.findViewById(R.id.listviewmy);
        String[]keys={"img","title","money"};
        int[]ids={R.id.item_img,R.id.item_title,R.id.item_money};
        MyLikeAdapter simpleAdapter=new MyLikeAdapter(v.getContext(),lists,R.layout.collection_listview,keys,ids,pid);
        listView.setAdapter(simpleAdapter);
        //构造map
        for(int i=0;i<likes.size();i++){
            Map<String,Object>map=new HashMap<>();
            map.put("img",imgIds[i]);
            map.put("title",Integer.toString(likes.get(i).getPid()));
            map.put("money",money[i]);
            lists.add(map);
        }
        return v;
    }

}
