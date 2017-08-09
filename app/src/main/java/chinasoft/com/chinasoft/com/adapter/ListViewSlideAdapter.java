package chinasoft.com.chinasoft.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import chinasoft.com.logindemo.R;
import chinasoft.com.util.MyCheckBox;

/**
 * Created by Ｓａｎｄｙ on 2017/8/5.
 * 侧滑Item的Adapter
 */

public class ListViewSlideAdapter extends SimpleAdapter {
    private List<String> bulbList;
    private List<? extends Map<String, ?>> mData;
    private Context context;
    private int[] mTo;
    private String[] mFrom;
    private int mResource;
    private ViewBinder mViewBinder;
    private OnClickListenerEditOrDelete onClickListenerEditOrDelete;
    private MyCheckBox checkBox;
    private LayoutInflater mInflater;
    public ListViewSlideAdapter(Context context,  List<? extends Map<String, ?>> data, int resource, String[] from,
                                int[] to){
        super(context, data, resource, from, to);
        this.bulbList=bulbList;
        this.context=context;
        mData = data;
        mResource  = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*@Override
    public int getCount() {
        return bulbList.size();
    }

    @Override
    public Object getItem(int position) {
        return bulbList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }*/

    public View getView(int position, final View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    /*public View getView(final int position, View convertView, ViewGroup parent) {
        final String bulb=bulbList.get(position);
        View view;
        final ViewHolder viewHolder;
        if(null == convertView) {
            view = View.inflate(context, R.layout.slidelist_item, null);
            viewHolder=new ViewHolder();
            viewHolder.tvName=(TextView)view.findViewById(R.id.tvName);
            viewHolder.img=(ImageView)view.findViewById(R.id.imgLamp);
            viewHolder.tvDelete=(TextView)view.findViewById(R.id.delete);
            viewHolder.tvEdit=(TextView)view.findViewById(R.id.tvEdit);
            viewHolder.checkBox=(MyCheckBox)view.findViewById(R.id.check2);
            view.setTag(viewHolder);//store up viewHolder
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.img.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tvName.setText(bulb);
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListenerEditOrDelete!=null){
                    onClickListenerEditOrDelete.OnClickListenerDelete(position);
                }
            }
        });
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListenerEditOrDelete!=null){
                    onClickListenerEditOrDelete.OnClickListenerEdit(position);
                }
            }
        });

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.checkBox.toggle();
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView tvName,tvEdit,tvDelete;
        ImageView img;
        MyCheckBox checkBox;
    }*/

    public interface OnClickListenerEditOrDelete{
        void OnClickListenerEdit(int position);
        void OnClickListenerDelete(int position);
        void OnClickListenerToggle(int position);
        void OnClickListenerUnToggle(int position);
    }

    public void setOnClickListenerEditOrDelete(OnClickListenerEditOrDelete onClickListenerEditOrDelete1){
        this.onClickListenerEditOrDelete=onClickListenerEditOrDelete1;
    }

    private View createViewFromResource(final int position, View convertView,
                                        ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);

            final int[] to = mTo;
            final int count = to.length;
            final View[] holder = new View[count];

            for (int i = 0; i < count; i++) {
                holder[i] = v.findViewById(to[i]);
            }

            v.setTag(holder);
        } else {
            v = convertView;
        }
        bindView(position, v);
        TextView tvDelete=(TextView)v.findViewById(R.id.delete);
        TextView tvEdit=(TextView)v.findViewById(R.id.tvEdit);
        final MyCheckBox checkBox=(MyCheckBox)v.findViewById(R.id.check2);
        final int k = position;
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListenerEditOrDelete!=null){
                    onClickListenerEditOrDelete.OnClickListenerDelete(k);
                }
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListenerEditOrDelete!=null){
                    onClickListenerEditOrDelete.OnClickListenerEdit(k);
                }
            }
        });

       checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListenerEditOrDelete!=null){
                    if(checkBox.isChecked())
                    {
                        onClickListenerEditOrDelete.OnClickListenerUnToggle(k);
                    }
                    else {
                        onClickListenerEditOrDelete.OnClickListenerToggle(k);
                    }
                }
                checkBox.toggle();

            }
        });


        return v;
    }

    private void bindView(int position, View view) {
        final Map dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }
        final ViewBinder binder = mViewBinder;
        final View[] holder = (View[]) view.getTag();
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View v = holder[i];
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }
                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }
                if (!bound) {
                    if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() +
                                    " should be bound to a Boolean, not a " + data.getClass());
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        //setViewText((TextView) v, text);
                        ((TextView) v).setText(text);
                    } else if (v instanceof ImageView) {
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);
                        } else {
                            setViewImage((ImageView) v, text);
                        }
                    } else if (v instanceof RatingBar) {
                        float score = Float.parseFloat(data.toString());  //备注2
                        ((RatingBar) v).setRating(score);
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this SimpleAdapter");
                    }
                }
            }
        }
    }




    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }

}
