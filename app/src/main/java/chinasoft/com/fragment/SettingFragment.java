package chinasoft.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chinasoft.com.logindemo.R;

/**
 * Created by Ｓａｎｄｙ on 2017/8/5.
 */

public class SettingFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.setting_fragment,container,false);

        return v;
    }
}
