package chinasoft.com.logindemo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import chinasoft.com.chinasoft.com.adapter.MenuAdapter;
import chinasoft.com.fragment.AdminFragment;
import chinasoft.com.fragment.CustomerFragment;
import chinasoft.com.fragment.OrderFragment;
import chinasoft.com.fragment.ProductFragment;
import chinasoft.com.fragment.SettingFragment;
import chinasoft.com.fragment.StorageFragment;
import chinasoft.com.util.SlidingMenu;

public class AdminMain extends AppCompatActivity {

    private SlidingMenu slideMenu1;//滑动菜单
    private ListView lv_menu;//滑动菜单项
    private MenuAdapter menuAdapter;//滑动菜单适配器
    private ImageView iv_menu;

    private Fragment contentFragment;
    private android.app.FragmentManager fragmentManager;
    private android.app.FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        initView();

        initUi();//初始化管理者对象

    }

    private void initView() {
        slideMenu1 = (SlidingMenu) findViewById(R.id.slideMenu1);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        menuAdapter = new MenuAdapter(this);
        lv_menu.setAdapter(menuAdapter);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu1.toggle();
            }
        });
        slideMenu1.setOnStatusListener(new SlidingMenu.OnStatusListener() {

            @Override
            public void statusChanged(SlidingMenu.Status status) {
                if (status == SlidingMenu.Status.Open) {
                    Toast.makeText(AdminMain.this, "Open", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminMain.this, "Close", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //为滑动菜单项添加点击事件
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("info",Integer.toString(position));
                transaction=fragmentManager.beginTransaction();
                switch(position){
                    case 0:
                        contentFragment = new AdminFragment();
                        transaction.replace(R.id.adminframepage,contentFragment);
                        break;
                    case 1:
                        contentFragment = new CustomerFragment();
                        transaction.replace(R.id.adminframepage,contentFragment);
                        break;
                    case 2:
                        contentFragment = new ProductFragment();

                        transaction.replace(R.id.adminframepage,contentFragment);
                        break;
                    case 3:
                        contentFragment = new StorageFragment();
                        transaction.replace(R.id.adminframepage,contentFragment);
                        break;
                    case 4:
                        contentFragment = new OrderFragment();
                        transaction.replace(R.id.adminframepage,contentFragment);
                        break;
                    case 5:
                        contentFragment = new SettingFragment();
                        transaction.replace(R.id.adminframepage,contentFragment);
                        break;
                    case 6:
                        Intent intent = new Intent(view.getContext(), ChangeActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;

                }
                transaction.commit();

            }
        });
    }

    //初始管理者页面的Framepage
    private void initUi(){
        //初始化管理者
        fragmentManager = getFragmentManager();
        transaction =fragmentManager.beginTransaction();
        Fragment init=new AdminFragment();
        transaction.replace(R.id.adminframepage,init,"fragment");
        transaction.commit();
    }

}
