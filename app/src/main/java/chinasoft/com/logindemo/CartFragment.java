package chinasoft.com.logindemo;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinasoft.com.chinasoft.com.adapter.ListViewSlideAdapter;
import chinasoft.com.dbutil.CartHelper;
import chinasoft.com.dbutil.LikeHelper;
import chinasoft.com.util.MyCheckBox;
import chinasoft.com.util.SlideListView;
import chinasoft.com.vo.Cart;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class CartFragment extends Fragment {
    private List<Map<String,Object>> lists=new ArrayList<>();
    private SlideListView listView;
    private List<String> list=new ArrayList<String>();
    private ListViewSlideAdapter listViewSlideAdapter;//适配器
    private MyCheckBox checkBoxSample2;
    private MyCheckBox checkBox;
    private TextView totalPrice;
    private List<Integer> selectPid = new ArrayList<>();
    private List<Integer> selectPrice = new ArrayList<>();
    private Integer tprice=0;//总计价格
    private List<Integer> number = new ArrayList<>();

    private int[] image={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,
            R.drawable.p7,R.drawable.p8,R.drawable.p9,R.drawable.p10};
    private List<Integer> pid = new ArrayList<>();
    private String[] title={"薏仁水","sofina妆前乳","DMC欣兰冻膜","肌美精3D面膜","城野医生毛孔收敛水","sana豆乳化妆水","sana豆乳乳液",
            "sana叶绿素美背喷雾","canmake透亮美肌粉饼","5色眼影14色"};
    private String[] price={"￥75","￥110","￥178","￥65","￥125","￥78","￥78","￥90","￥95","￥89"};
    private Integer[] pri={75,110,178,65,125,78,90,95,89};
    private String[] place={"日本","日本","台湾","日本","日本","日本","日本","日本","日本","日本"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.cart_noempty,container,false);
        totalPrice = (TextView)v.findViewById(R.id.totalprice);
        initView(v);

        checkBoxSample2 = (MyCheckBox) v.findViewById(R.id.check2);
        checkBoxSample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxSample2.toggle();
                if (checkBoxSample2.isChecked()) {
                    tprice = 0;
                    for (int i = 0; i < pid.size(); i++) {
                        tprice += pri[i]*number.get(i);
                    }
                    for (int i = 0; i < listView.getChildCount(); i++) {
                        View v1 = listView.getChildAt(i);
                        MyCheckBox cb = (MyCheckBox) v1.findViewById(R.id.check2);
                        cb.setChecked(true);
                    }
                    totalPrice.setText("￥" + Integer.toString(tprice));

                }
                else
                {
                    tprice = 0;
                    for (int i = 0; i < listView.getChildCount(); i++) {
                        View v1 = listView.getChildAt(i);
                        MyCheckBox cb = (MyCheckBox) v1.findViewById(R.id.check2);
                        cb.setChecked(false);
                    }
                    totalPrice.setText("￥" + Integer.toString(tprice));
                }
            }

        });
        return v;
    }


    private void initView(final View v){
        listView=(SlideListView)v.findViewById(R.id.list);
        //listViewSlideAdapter=new ListViewSlideAdapter(v.getContext(),list);
        //listView.setAdapter(listViewSlideAdapter);
        CartHelper cartHelper = new CartHelper();
        SharedPreferences sp = v.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sp.getString("username","");
        List<Cart> carts = cartHelper.findAll(username);
        for(int i=0;i<carts.size();i++)
        {
            pid.add(carts.get(i).getPid());
            number.add(carts.get(i).getNum());
        }
        cartHelper.close();

        int[] Ids={R.id.imgLamp,R.id.tvName,R.id.tvContent,R.id.tvPrice,R.id.tvNumber};
        String[] keys={"image","title","place","price","number"};
        ListViewSlideAdapter listViewSlideAdapter=new ListViewSlideAdapter(v.getContext(),lists,R.layout.slidelist_item,keys,Ids);
        listView.setAdapter(listViewSlideAdapter);
        LikeHelper likeHelper = new LikeHelper();
        for(int i=0;i<pid.size();i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image[i]);
            map.put("title", Integer.toString(pid.get(i)));
            map.put("place", place[i]);
            map.put("price", price[i]);
            map.put("number","x"+Integer.toString(number.get(i)));
            lists.add(map);
            listViewSlideAdapter.setOnClickListenerEditOrDelete(new ListViewSlideAdapter.OnClickListenerEditOrDelete() {
                @Override
                public void OnClickListenerEdit(int position) {
                    final EditText et = new EditText(v.getContext());
                    final int k = position;

                    new AlertDialog.Builder(v.getContext()).setTitle("编辑商品数量")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(et)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String input = et.getText().toString();
                                    if (input.equals("")) {
                                        Toast.makeText(v.getContext(), "数量不能为空！" + input, Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        CartHelper cartHelper = new CartHelper();
                                        cartHelper.update(pid.get(k),Integer.valueOf(input));
                                        cartHelper.close();
                                        Fragment contentFragment;
                                        android.app.FragmentManager fragmentManager;
                                        android.app.FragmentTransaction transaction;
                                        fragmentManager = getFragmentManager();
                                        transaction =fragmentManager.beginTransaction();
                                        Fragment init=new CartFragment();
                                        transaction.replace(R.id.fragmentPage,init,"fragment");
                                        transaction.commit();
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();

                }

                @Override
                public void OnClickListenerDelete(int position) {
                    final int k = position;
                    new AlertDialog.Builder(v.getContext()).setTitle("确认要删除该商品吗？")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                   CartHelper cartHelper = new CartHelper();
                                    cartHelper.delete(pid.get(k));
                                    cartHelper.close();
                                    android.app.FragmentManager fragmentManager;
                                    android.app.FragmentTransaction transaction;
                                    fragmentManager = getFragmentManager();
                                    transaction =fragmentManager.beginTransaction();
                                    Fragment init=new CartFragment();
                                    transaction.replace(R.id.fragmentPage,init,"fragment");
                                    transaction.commit();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();

                   // Toast.makeText(v.getContext(), "delete position: " + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnClickListenerToggle(int position) {
                       selectPid.add(pid.get(position));
                       tprice+=pri[position]*number.get(position);
                       totalPrice = (TextView)v.findViewById(R.id.totalprice);
                       totalPrice.setText("￥"+Integer.toString(tprice));

                }

                @Override
                public void OnClickListenerUnToggle(int position) {
                    tprice-=pri[position]*number.get(position);
                    totalPrice = (TextView)v.findViewById(R.id.totalprice);
                    totalPrice.setText("￥"+Integer.toString(tprice));
                }
            });

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
