package chinasoft.com.chinasoft.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import chinasoft.com.logindemo.R;
import chinasoft.com.util.Indexer;
import chinasoft.com.vo.Customer;

/**
 * Created by Ｓａｎｄｙ on 2017/8/7.
 */

public class CustomerSelectorAdapter extends BaseAdapter implements Indexer {
    private final LayoutInflater mInflate;
    private List<Customer> mCustomer;
    private Context mContext;
    private HashMap<String, Integer> indexMap = new HashMap<String, Integer>();

    public CustomerSelectorAdapter(Context context, List<Customer> customers) {
        mCustomer = customers;
        mContext = context;
        mInflate = LayoutInflater.from(mContext);
        // 列表特征和分组首项进行关联
        for (int i = 0; i < mCustomer.size(); i++) {
            Customer customer = mCustomer.get(i);
            String index = customer.getIndex();
            if(index == null || "".equals(index)) continue;
            String section = index.toUpperCase().substring(0, 1);
            if(!indexMap.containsKey(section)){
                indexMap.put(section, i);
            }
        }
    }
    public int getCount() {
        return mCustomer.size();
    }
    public Object getItem(int position) {
        return mCustomer.get(position);
    }
    public long getItemId(int position) {
        return mCustomer.get(position).getId().hashCode();
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflate.inflate(R.layout.customer_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
            holder.tvSection = (TextView)convertView.findViewById(R.id.tvSection);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Customer customer = mCustomer.get(position);
        holder.tvName.setText(customer.getUsername());



        String index = customer.getIndex();
        char idFirstChar = index.toUpperCase().charAt(0);
        if (position == 0) {
            setIndex(holder.tvSection, String.valueOf(idFirstChar));
        } else {
            String preLabel = mCustomer.get(position - 1).getIndex();
            char preFirstChar = preLabel.toUpperCase().charAt(0);
            if (idFirstChar != preFirstChar) { // diff group
                setIndex(holder.tvSection, String.valueOf(idFirstChar));
            } else { // same group
                holder.tvSection.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private void setIndex(TextView section, String str){
        section.setVisibility(View.VISIBLE);
        if("#".equals(str)) section.setText("当前城市");
        else section.setText(str);
    }

    @Override
    public int getStartPositionOfSection(String section) {
        if(indexMap.containsKey(section))
            return indexMap.get(section);
        return -1;
    }

    static class ViewHolder{
        TextView tvName;
        TextView tvSection;
    }





}

