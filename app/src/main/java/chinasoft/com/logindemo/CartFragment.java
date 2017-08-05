package chinasoft.com.logindemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chinasoft.com.chinasoft.com.adapter.ListViewSlideAdapter;
import chinasoft.com.util.MyCheckBox;
import chinasoft.com.util.SlideListView;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class CartFragment extends Fragment {
    private SlideListView listView;
    private List<String> list=new ArrayList<String>();
    private ListViewSlideAdapter listViewSlideAdapter;
    private MyCheckBox checkBoxSample2;
    private MyCheckBox checkBox;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.cart_noempty,container,false);
        getData();
        initView(v);

        checkBoxSample2 = (MyCheckBox) v.findViewById(R.id.check2);
        checkBoxSample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxSample2.toggle();
            }
        });
        return v;
    }


    private void initView(final View v){
        listView=(SlideListView)v.findViewById(R.id.list);
        listViewSlideAdapter=new ListViewSlideAdapter(v.getContext(),list);
        listView.setAdapter(listViewSlideAdapter);
        listViewSlideAdapter.setOnClickListenerEditOrDelete(new ListViewSlideAdapter.OnClickListenerEditOrDelete() {
            @Override
            public void OnClickListenerEdit(int position) {
                Toast.makeText(v.getContext(), "edit position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnClickListenerDelete(int position) {
                Toast.makeText(v.getContext(), "delete position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(){
        for (int i=0;i<20;i++){
            list.add(new String("第"+i+"个item"));
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
