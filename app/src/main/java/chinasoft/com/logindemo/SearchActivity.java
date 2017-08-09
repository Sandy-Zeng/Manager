package chinasoft.com.logindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chinasoft.com.dbutil.RecordSQLiteOpenHelper;
import chinasoft.com.util.FlowLayout;
import chinasoft.com.vo.Record;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@ContentView(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity {

    private static final int MESSAGE_SHOW_KEYBOARD = 1;
    private static final int MESSAGE_SHOW_EDIT = 2;
    public static final int DURATION = 300;

    private static final AccelerateDecelerateInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final String SCALE_WIDTH = "SCALE_WIDTH";
    private static final String SCALE_HEIGHT = "SCALE_HEIGHT";
    private static final String TRANSITION_X = "TRANSITION_X";
    private static final String TRANSITION_Y = "TRANSITION_Y";

    private InputMethodManager inputMethodManager;

    private Activity instance = SearchActivity.this;

    /**
     * 存储图片缩放比例和位移距离
     */
    private Bundle mScaleBundle = new Bundle();
    private Bundle mTransitionBundle = new Bundle();

    /**
     * 屏幕宽度和高度
     */
    private int mScreenWidth;
    private int mScreenHeight;

    /**
     * 上一个界面图片的宽度和高度
     */
    private int mOriginWidth;
    private int mOriginHeight;

    /**
     * 上一个界面图片的位置信息
     */
    private Rect mRect;

    private EditText searchEdit;

    @ViewInject(R.id.cancle)
    private TextView cancle;
    @ViewInject(R.id.searchLayout)
    private LinearLayout layout;
    @ViewInject(R.id.historyLayout)
    private FlowLayout historyLayout;
    @ViewInject(R.id.fenleiLayout)
    private FlowLayout fenleiLayout;
    @ViewInject(R.id.brandLayout)
    private FlowLayout brandLayout;
    private List<String> historyRecord=new ArrayList<>();//搜索历史的数组
    private List<String> brand= new ArrayList<>();
    private List<String> type=new ArrayList<>();
    //private RecordSQLiteOpenHelper helper ;
    private SQLiteDatabase db;

    @Override
    public void onBackPressed() {
        // 使用退场动画
        runExitAnim();
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        initData();

        //historyLayout.bindView(historyRecord,SearchActivity.this);
        //为流式布局设置
        initLayout(historyLayout,historyRecord,0);
        initLayout(fenleiLayout,type,1);
        initLayout(brandLayout,brand,2);


        searchEdit.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键


            // 修改回车键功能
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 隐藏键盘，这里getCurrentFocus()需要传入Activity对象，如果实际不需要的话就不用隐藏键盘了，免得传入Activity对象，这里就先不实现了
//                    ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Request request =new Request.Builder().url("http://192.168.40.14:8080/dgManager/Product_findAllProduct")
                            .get()
                            .build();
                    //Request request=builder.url("http://192.168.40.14:8080/dgManager/userlogin").post(formBody).build();
                    exec(request);



                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    RecordSQLiteOpenHelper recordSQLiteOpenHelper=new RecordSQLiteOpenHelper();
                    boolean hasData = recordSQLiteOpenHelper.hasRecord(searchEdit.getText().toString().trim());
                    if (!hasData) {
                        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
                        recordSQLiteOpenHelper.insert(searchEdit.getText().toString().trim(),sp.getString("username",""));
                    }
                    recordSQLiteOpenHelper.closeDB();
                    //根据输入的内容模糊查询商品，并跳转到另一个界面，这个需要根据需求实现
                    Toast.makeText(SearchActivity.this, "点击搜索", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

    }

    private void exec(Request request){
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info","链接失败");
                /*Message message =new Message();
                message.what=1;
                message.obj ="ok";
                handler.sendMessage(message);*/
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("info","链接成功");
                String s=response.body().string();
                Message message =new Message();
                message.what=1;
                message.obj =s;
                handler.sendMessage(message);
            }
        });
    }

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==1){
                String result=(String) msg.obj;
                //String result="ok";
                Log.i("info",result);
                if(result.equals("ok")) {

                }
            }
        }

    };


    //初始化数据
    protected  void initData(){
        RecordSQLiteOpenHelper recordSQLiteOpenHelper = new RecordSQLiteOpenHelper();
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        List<Record> record= recordSQLiteOpenHelper.findAll(sp.getString("username",""));
        for(Record r : record){
            historyRecord.add(r.getName());
        }
        type.add("面膜"); type.add("香水"); type.add("护肤套装"); type.add("彩妆"); type.add("洁面"); type.add("其他");
    }

    //初始化流式布局
    protected void initLayout(FlowLayout flowlayout,List<String> data,int tag){
        LayoutInflater inflater=LayoutInflater.from(SearchActivity.this);
        for(int i=0;i<data.size();i++) {
            LinearLayout layout=new LinearLayout(SearchActivity.this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView view = (TextView) inflater.inflate(R.layout.flowlayout_item,null);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            view.setLayoutParams(params);
            view.setText(data.get(i));

            //设置不同的搜索响应
            switch(tag){
                //按照搜索历史
                case 0:
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    break;
                //按照分类
                case 1:
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    break;
                //按照品牌
                case 2:
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    break;
                default:
                    break;

            }
            layout.addView(view);
            flowlayout.addView(layout);
        }

    }


    protected void initView() {
        // 获得屏幕尺寸
        getScreenSize();

        // 初始化界面
        searchEdit = (EditText) findViewById(R.id.gosearch);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        // 初始化场景
        initial();

        // 设置入场动画
        runEnterAnim();

        //动态显示搜索结果
        showSearchResult();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_SHOW_KEYBOARD:
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    break;
                case MESSAGE_SHOW_EDIT:
                    //mImageView.setVisibility(View.GONE);
                    searchEdit.setVisibility(View.VISIBLE);
                    searchEdit.requestFocus();
                    break;
            }
        }
    };


    /**
     * 初始化场景
     */
    private void initial() {
        // 获取上一个界面传入的信息
        mRect = getIntent().getSourceBounds();

        // 获取上一个界面中，图片的宽度和高度
        mOriginWidth = mRect.right - mRect.left;
        mOriginHeight = mRect.bottom - mRect.top;

        // 设置 ImageView 的位置，使其和上一个界面中图片的位置重合
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mOriginWidth, mOriginHeight);
        params.setMargins(mRect.left, mRect.top - getStatusBarHeight(), mRect.right, mRect.bottom);
        //searchEdit.setLayoutParams(params);

    }

    /**
     * 计算图片缩放比例，以及位移距离
     */
    private void getBundleInfo(Bitmap bitmap) {
        // 计算图片缩放比例，并存储在 bundle 中
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            mScaleBundle.putFloat(SCALE_WIDTH, (float) mScreenWidth / mOriginWidth);
            mScaleBundle.putFloat(SCALE_HEIGHT, (float) bitmap.getHeight() / mOriginHeight);
        } else {
            mScaleBundle.putFloat(SCALE_WIDTH, (float) bitmap.getWidth() / mOriginWidth);
            mScaleBundle.putFloat(SCALE_HEIGHT, (float) mScreenHeight / mOriginHeight);
        }
        // 计算位移距离，并将数据存储到 bundle 中
        mTransitionBundle.putFloat(TRANSITION_X, mScreenWidth / 2 - (mRect.left + (mRect.right - mRect.left) / 2));

//        mTransitionBundle.putFloat(TRANSITION_Y, mScreenHeight / 2 - (mRect.top + (mRect.bottom - mRect.top) / 2));
        mTransitionBundle.putFloat(TRANSITION_Y, -(mRect.top-getStatusBarHeight()));
    }

    /**
     * 模拟入场动画
     */
    private void runEnterAnim() {
        searchEdit.animate()
                .setInterpolator(DEFAULT_INTERPOLATOR)
                .setDuration(DURATION)
               // .scaleX(mScaleBundle.getFloat(SCALE_WIDTH))
                //.scaleY(mScaleBundle.getFloat(SCALE_HEIGHT))
                .translationX(mTransitionBundle.getFloat(TRANSITION_X))
                .translationY(mTransitionBundle.getFloat(TRANSITION_Y))
                .start();
        searchEdit.setVisibility(View.VISIBLE);

        //add
        mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_EDIT,DURATION);
        mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_KEYBOARD,DURATION*2);
    }

    /**
     * 模拟退场动画
     */
    @SuppressWarnings("NewApi")
    private void runExitAnim() {
        //add
        cancle.setVisibility(View.GONE);
        searchEdit.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        searchEdit.animate()
                .setInterpolator(DEFAULT_INTERPOLATOR)
                .setDuration(DURATION)
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(0)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
    }

    /**
     * 获取状态栏高度
     */
    private int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    private void showSearchResult(){
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //搜索的匹配算法
                Log.d("SearchActivity"," afterTextChanged 调用了 s="+s.toString());

            }
        });
    }

    @Event (value={R.id.cancle},type= View.OnClickListener.class)
    private void doEvent(View v){
        switch (v.getId()) {
            case R.id.cancle:
                runExitAnim();
                Intent intent=new Intent(SearchActivity.this,ShouyeDemo.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                break;
            default:
                break;
        }


    }



}
