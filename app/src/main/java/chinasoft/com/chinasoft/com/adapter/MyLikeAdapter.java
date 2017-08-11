package chinasoft.com.chinasoft.com.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import chinasoft.com.dbutil.CartHelper;
import chinasoft.com.dbutil.HistoryHelper;
import chinasoft.com.dbutil.LikeHelper;
import chinasoft.com.logindemo.HistoryActivity;
import chinasoft.com.logindemo.MyFragment;
import chinasoft.com.logindemo.R;

/**
 * Created by Ｓａｎｄｙ on 2017/8/9.
 */

public class MyLikeAdapter extends SimpleAdapter {
    private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;
    private List<? extends Map<String, ?>> mData;
    private int mResource;
    private int mDropDownResource;
    private LayoutInflater mInflater;
    private List<Integer> pid;
    private List<String> mtitle;
    private List<String> mplace;
    private List<String> mprice;
    private Context mcontext;
    private FragmentManager fragmentManager = null;
    public MyLikeAdapter(Context context,
                         List<? extends Map<String, ?>> data, int resource, String[] from,
                         int[] to, List<Integer> pids, List<String> title, List<String> place, List<String> price,
                         FragmentManager mfragmentManager) {
        super(context, data, resource, from, to);
        mData = data;
        mResource = mDropDownResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pid = pids;
        mtitle = title;
        mplace = place;
        mprice = price;
        mcontext = context;
        fragmentManager = mfragmentManager;
    }

    public MyLikeAdapter(Context context,
                         List<? extends Map<String, ?>> data, int resource, String[] from,
                         int[] to, List<Integer> pids, List<String> title, List<String> place, List<String> price
    ) {
        super(context, data, resource, from, to);
        mData = data;
        mResource = mDropDownResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pid = pids;
        mtitle = title;
        mplace = place;
        mprice = price;
        mcontext = context;

    }
    public View getView(int position, final View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    public interface OnClickListenerLike {
        void OnClickListenercancelLike(int position);

    }

    private View createViewFromResource(int position, View convertView,
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
        final ImageView cart = (ImageView)v.findViewById(R.id.addcart);
        final Integer k = position;
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = pid.get(k);
                CartHelper cartHelper = new CartHelper();
                cart.setImageResource(R.drawable.collection_cart_full);
                if(cartHelper.hasCart(id))
                {
                    cartHelper.update(1,id);
                }
                else {
                    SharedPreferences sp = v.getContext().getSharedPreferences("user", v.getContext().MODE_PRIVATE);
                    //获取Editor对象
                    String username = sp.getString("username", "");
                    cartHelper.add(pid.get(k), 1, username, mprice.get(k), mtitle.get(k), mplace.get(k));
                }
                cartHelper.close();
                Toast.makeText(v.getContext(),"成功加入购物车",Toast.LENGTH_SHORT).show();
            }
        });
        if (fragmentManager != null) {
            final ImageView like = (ImageView) v.findViewById(R.id.cancelcollection);
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer id = pid.get(k);
                    LikeHelper likeHelper = new LikeHelper();
                    likeHelper.deleteByPid(id);
                    android.app.FragmentTransaction transaction;
                    transaction = fragmentManager.beginTransaction();
                    Fragment init = new MyFragment();
                    transaction.replace(R.id.fragmentPage, init, "fragment");
                    transaction.commit();
                    Toast.makeText(v.getContext(), "成功取消收藏", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            final ImageView delete = (ImageView) v.findViewById(R.id.deleteHistory);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer id = pid.get(k);
                    HistoryHelper historyHelper = new HistoryHelper();
                    historyHelper.delete(id);
                    Intent intent = new Intent(mcontext, HistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(intent);
                    Toast.makeText(v.getContext(), "成功删除", Toast.LENGTH_SHORT).show();

                }
            });
        }

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
