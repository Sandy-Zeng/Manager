package chinasoft.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chinasoft.com.logindemo.R;

/**
 * Created by Ｓａｎｄｙ on 2017/8/5.
 */

public class AdminFragment extends android.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.admin_main,container,false);



        return v;
    }
}
