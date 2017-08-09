package chinasoft.com.logindemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteFragmentTest extends Activity {
    private ImageView note;
    private ImageView collection;
    private Fragment contentFragmemt;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my);
        initUi();
    }
    private void initUi(){
        note=(ImageView) findViewById(R.id.note);
        collection=(ImageView) findViewById(R.id.collection);
        note.setOnClickListener(itemclick);
        collection.setOnClickListener(itemclick);
        //fragment管理者
        fragmentManager=getFragmentManager();
        transaction=fragmentManager.beginTransaction();
        Fragment init=new My_Note_Fragment();
        transaction.replace(R.id.fragmentPageMy,init,"fragment");//
        transaction.commit();
    }
    private View.OnClickListener itemclick=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
           /* note.setBackgroundColor(0Xffffffff);
            note.setTextColor(0XFFE4BA3F);
            collection.setBackgroundColor(0Xffffffff);
            collection.setTextColor(0XFFE4BA3F);*/
            TextView textView=(TextView)view;
           /* textView.setBackgroundColor(0XFFE4BA3F);
            textView.setTextColor(0xff000000);*/
            transaction=fragmentManager.beginTransaction();
            switch (view.getId()){
                case R.id.note:
                    contentFragmemt=new My_Note_Fragment();
                    transaction.replace(R.id.fragmentPageMy,contentFragmemt);
                    break;
                case R.id.collection:
                    contentFragmemt=new My_Note_Fragment();
                    transaction.replace(R.id.fragmentPageMy,contentFragmemt);
                    break;
                default:
                    break;
            }
            transaction.commit();
        }
    };
}
