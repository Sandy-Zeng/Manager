package chinasoft.com.logindemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class MyFragment extends Fragment {
    private ImageView note;
    private ImageView collection;
    private Fragment contentFragmemt;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ImageView history;
    private ImageView setting;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_my,container,false);
        note=(ImageView)v. findViewById(R.id.note);
        collection=(ImageView)v. findViewById(R.id.collection);
        //fragment管理者
        fragmentManager=getFragmentManager();
        transaction=fragmentManager.beginTransaction();
      // Fragment init=new My_Note_Fragment();
        Fragment init=new My_Collection_Fragment();
        transaction.replace(R.id.fragmentPageMy,init,"fragment");//
        transaction.commit();

        //足迹
        history = (ImageView) v.findViewById(R.id.searchHistory);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        //设置
        setting = (ImageView) v.findViewById(R.id.gosetting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConfigActivity.class);
                startActivity(intent);
            }
        });


        return v ;
    }
}
