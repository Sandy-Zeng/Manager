package chinasoft.com.fragmentitem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chinasoft.com.logindemo.R;

/**
 * Created by Ｓａｎｄｙ on 2017/8/10.
 */

public class FragmentItem3 extends android.support.v4.app.Fragment {
    private View rootView;
    private TextView brand;
    private TextView country;
    private TextView detail;
    private TextView hotLevel;
    private TextView type;

    private String brandName;
    private String cn;
    private String de;
    private String hl;
    private String ty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.detail_layout, container, false);
            brand = (TextView) rootView.findViewById(R.id.brand);
            hotLevel = (TextView) rootView.findViewById(R.id.hotLevel);
            country = (TextView) rootView.findViewById(R.id.country);
            detail = (TextView) rootView.findViewById(R.id.detail);
            type = (TextView) rootView.findViewById(R.id.type);


            brand.setText(brandName);
            hotLevel.setText(hl);
            country.setText(cn);
            detail.setText(de);
            type.setText(ty);


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

    public void set(String b, String c, String h, String d, String t) {
        brandName = b;
        cn = c;
        hl = h;
        de = d;
        ty = t;

    }


}
