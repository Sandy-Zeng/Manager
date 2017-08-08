package chinasoft.com.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chinasoft.com.adapter.MenuListAdapter;

@ContentView(R.layout.activity_drop_sort)
public class DropSortActivity extends AppCompatActivity {
    @ViewInject(R.id.text)
    private TextView text;

    //菜单标题
    private String headers[] = {"排序", "国家", "品牌"};
    private ListView listView1;
    private ListView listView2;
    private ListView listView3;
    private MenuListAdapter mMenuAdapter1;
    private MenuListAdapter mMenuAdapter2;
    private MenuListAdapter mMenuAdapter3;

    private DropDownMenu mDropDownMenu;

    private String sorts[] = {"不限", "好评由高到低", "销量由高到低", "价格由高到低", "价格由低到高"};

    private String countries[] = {"不限", "日本", "美国", "台湾", "韩国"};

    private String brands[] = {"不限", "canmake", "mac","sana","tom ford","城野医生","娥佩兰","欣兰","苏菲娜","高姿"};

    private List<View> popupViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_drop_sort);
        x.view().inject(this);

        initView();
    }

    private void initView() {

        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);


        //init menu listview

        //这里是每个下拉菜单之后的布局,目前只是简单的listview作为展示
        listView1 = new ListView(DropSortActivity.this);
        listView2 = new ListView(DropSortActivity.this);
        listView3 = new ListView(DropSortActivity.this);

        listView1.setDividerHeight(0);
        listView2.setDividerHeight(0);
        listView3.setDividerHeight(0);

        mMenuAdapter1 = new MenuListAdapter(DropSortActivity.this, Arrays.asList(sorts));
        mMenuAdapter2 = new MenuListAdapter(DropSortActivity.this, Arrays.asList(countries));
        mMenuAdapter3 = new MenuListAdapter(DropSortActivity.this, Arrays.asList(brands));

        listView1.setAdapter(mMenuAdapter1);
        listView2.setAdapter(mMenuAdapter2);
        listView3.setAdapter(mMenuAdapter3);

        popupViews.add(listView1);
        popupViews.add(listView2);
        popupViews.add(listView3);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDropDownMenu.setTabText(sorts[position]);
                text.setText(sorts[position]);
                mDropDownMenu.closeMenu();
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDropDownMenu.setTabText(countries[position]);
                text.setText(countries[position]);
                mDropDownMenu.closeMenu();
            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDropDownMenu.setTabText(brands[position]);
                text.setText(brands[position]);
                mDropDownMenu.closeMenu();
            }
        });


        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews);

    }
}
