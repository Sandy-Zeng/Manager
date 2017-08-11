package chinasoft.com.fragmentitem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import chinasoft.com.service.Message;


/**
 * Created by lenovo on 2017/8/6.
 */

public class FragmentItem1 extends Fragment {
    private View rootView;
    private List<Map<String, Object>> lists = new ArrayList<>();
    private ListView listView;
    private List<Message> message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.commet_layout, container, false);
            listView = (ListView) rootView.findViewById(R.id.commentlist);
            int[] Ids = {R.id.username, R.id.time, R.id.content};
            String[] keys = {"username", "time", "content"};
            SimpleAdapter simpleAdapter = new SimpleAdapter(rootView.getContext(), lists, R.layout.cl_item, keys, Ids);
            for (int i = 0; i < message.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("username", message.get(i).getUsername());
                map.put("content", message.get(i).getContent());
                map.put("time", message.get(i).getTime());
                lists.add(map);
            }
            listView.setAdapter(simpleAdapter);

        } else {
            ViewGroup v = ((ViewGroup) (rootView.getParent()));
            if (v != null) {
                v.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setMessage(List<Message> messageList) {
        message = messageList;
    }


}
