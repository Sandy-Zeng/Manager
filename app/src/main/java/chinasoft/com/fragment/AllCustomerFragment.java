package chinasoft.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chinasoft.com.chinasoft.com.adapter.CustomerSelectorAdapter;
import chinasoft.com.dbutil.CustomerHelper;
import chinasoft.com.logindemo.R;
import chinasoft.com.util.SectionBar;
import chinasoft.com.vo.Customer;

/**
 * Created by Ｓａｎｄｙ on 2017/8/7.
 */

public class AllCustomerFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    private int position;

    ListView lvCity;
    SectionBar sbCity;


    static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302,
            2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
            4086, 4390, 4558, 4684, 4925, 5249, 5600 };
    // 存放国标一级汉字不同读音的起始区位码对应读音
    static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
            'y', 'z' };

    public static AllCustomerFragment newInstance(int position) {
        AllCustomerFragment f = new AllCustomerFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        View v=inflater.inflate(R.layout.fragment_allcustomer,container,false);
        fl.setLayoutParams(params);

        lvCity = (ListView)v.findViewById(R.id.lvCity);
        sbCity = (SectionBar)v.findViewById(R.id.sbCity);

        switch(position)
        {
            case 0:
                setAllCustomer(v);
                break;
            case 2:
                setVIPCustomer(v);
                break;
            case 3:
                setNotVIPCustomer(v);
                break;
            default:
                break;
        }

        return v;
    }

    private void setAllCustomer(View view) {
        List<Customer> customers = new ArrayList<Customer>();
        CustomerHelper customerHelper = new CustomerHelper();
        customers = customerHelper.findAll();
        for( Customer c : customers ){
            String name = c.getUsername();
            Log.i("info",name);
            String index = getSpells(c.getUsername());
            Log.i("info",index);
            c.setIndex1(getSpells(c.getUsername()));
        }
        Collections.sort(customers);
        CustomerSelectorAdapter adapter = new CustomerSelectorAdapter(view.getContext(),customers);
        lvCity.setAdapter(adapter);
        sbCity.setListView(lvCity);
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
            }
        });

    }

    private void setVIPCustomer(View view) {
        List<Customer> customers = new ArrayList<Customer>();
        CustomerHelper customerHelper = new CustomerHelper();
        customers = customerHelper.findAll();
        for( Customer c : customers ){
            String name = c.getUsername();
            Log.i("info",name);
            String index = getSpells(c.getUsername());
            Log.i("info",index);
            c.setIndex1(getSpells(c.getUsername()));
        }
        Collections.sort(customers);
        CustomerSelectorAdapter adapter = new CustomerSelectorAdapter(view.getContext(),customers);
        lvCity.setAdapter(adapter);
        sbCity.setListView(lvCity);
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
            }
        });

    }

    private void setNotVIPCustomer(View view) {
        List<Customer> customers = new ArrayList<Customer>();
        CustomerHelper customerHelper = new CustomerHelper();
        customers = customerHelper.findAll();
        for( Customer c : customers ){
            String name = c.getUsername();
            Log.i("info",name);
            String index = getSpells(c.getUsername());
            Log.i("info",index);
            c.setIndex1(getSpells(c.getUsername()));
        }
        Collections.sort(customers);
        CustomerSelectorAdapter adapter = new CustomerSelectorAdapter(view.getContext(),customers);
        lvCity.setAdapter(adapter);
        sbCity.setListView(lvCity);
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
            }
        });

    }


    public static String getSpells(String characters) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < characters.length(); i++) {

            char ch = characters.charAt(i);
            if ((ch >> 7) == 0) {
                // 判断是否为汉字，如果左移7为为0就不是汉字，否则是汉字
                return String.valueOf(characters.charAt(0));
            } else {
                char spell = getFirstLetter(ch);
                buffer.append(String.valueOf(spell));
            }
        }
        return buffer.toString();
    }

    // 获取一个汉字的首字母
    public static Character getFirstLetter(char ch) {

        byte[] uniCode = null;
        try {
            uniCode = String.valueOf(ch).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
            return null;
        } else {
            return convert(uniCode);
        }
    }

    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */
    static char convert(byte[] bytes) {
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i]
                    && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }
}
