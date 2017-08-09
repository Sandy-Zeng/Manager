package chinasoft.com.chinasoft.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chinasoft.com.logindemo.R;

/**
 * Created by Ｓａｎｄｙ on 2017/8/4.
 */

public class MenuAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public MenuAdapter(Context context) {
        this.context = context;
        list = new ArrayList();
        list.add("首页");
        list.add("客户管理");
        list.add("商品管理");
        list.add("库存管理");
        list.add("订单管理");
        list.add("设置");
        list.add("退出登录");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String item = list.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item.setText(item);
        return convertView;
    }

    static class ViewHolder {
        TextView tv_item;
    }
}
