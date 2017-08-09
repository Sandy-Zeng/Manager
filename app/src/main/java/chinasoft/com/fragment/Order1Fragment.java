package chinasoft.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chinasoft.com.logindemo.R;


public class Order1Fragment extends android.app.Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static Order1Fragment newInstance(int position) {
        Order1Fragment f = new Order1Fragment();
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
        View v=inflater.inflate(R.layout.fragment_order1,container,false);
       // TextView textView=(TextView) v.findViewById(R.id.order1);

        return v;
    }
}
