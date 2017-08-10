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
import android.widget.TextView;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class MyFragment extends Fragment {
    private ImageView note;
    private ImageView collection;
    private Fragment contentFragmemt;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ImageView go_track;
    private ImageView go_order;
    private TextView track;
    private TextView order;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_my,container,false);
        note=(ImageView)v. findViewById(R.id.note);
        collection=(ImageView)v. findViewById(R.id.collection);
        go_track= (ImageView) v.findViewById(R.id.go_track);
        track= (TextView) v.findViewById(R.id.track);
        go_order= (ImageView) v.findViewById(R.id.go_order);
        order= (TextView) v.findViewById(R.id.order);
        //fragment管理者
        fragmentManager=getFragmentManager();
        transaction=fragmentManager.beginTransaction();
      // Fragment init=new My_Note_Fragment();
        Fragment init=new My_Collection_Fragment();
        transaction.replace(R.id.fragmentPageMy,init,"fragment");//
        transaction.commit();




        go_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        go_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_order=new Intent(getActivity(),OrderListActivity.class);
                startActivity(go_to_order);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_order=new Intent(getActivity(),OrderListActivity.class);
                startActivity(go_to_order);
            }
        });

        return v ;
    }
}
