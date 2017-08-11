package chinasoft.com.logindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


@ContentView(R.layout.activity_location)
public class LocationActivity extends AppCompatActivity{
    @ViewInject(R.id.et_user)
    private EditText et_user;
    @ViewInject(R.id.et_telephone)
    private EditText et_telephone;

    @ViewInject(R.id.et_email)
    private EditText et_email;

    @ViewInject(R.id.et_location)
    private EditText et_location;

    @ViewInject(R.id.et_detail)
    private EditText et_detail;

    private SharedPreferences sp;
//    String store_user;
//    String store_telephone;
//    String store_email;
//    String store_location;
//    String store_detail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_location);
        x.view().inject(this);
        //存储


        sp=getSharedPreferences("location_Manager",MODE_PRIVATE);
        String user=sp.getString("user","");
        String telephone=sp.getString("telephone","");
        String email=sp.getString("email","");
        String location=sp.getString("location","");
        String detail=sp.getString("detail","");

        et_user.setText(user);
        et_telephone.setText(telephone);
        et_email.setText(email);
        et_location.setText(location);
        et_detail.setText(detail);

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("user",et_user.getText().toString());
        editor.putString("telephone",et_telephone.getText().toString());
        editor.putString("email",et_email.getText().toString());
        editor.putString("location",et_location.getText().toString());
        editor.putString("detail",et_detail.getText().toString());
        editor.commit();
    }
}
