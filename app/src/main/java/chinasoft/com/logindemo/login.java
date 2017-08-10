package chinasoft.com.logindemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import chinasoft.com.dbutil.CustomerHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class login extends Activity {
    private TextView register;
    private EditText username;
    private EditText password;
    private CheckBox remember;
    private SharedPreferences sp;


    private static final String TAG = "login";
    private static final String APP_ID = "1106266965";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==1){
                String result=(String) msg.obj;
                //String result="ok";
                Log.i("info",result);
                if(result.equals("ok")) {
                    //将当前登录用户的用户名和密码保存
                    String user=username.getText().toString();
                    String pwd = password.getText().toString();
                    Context context=login.this;
                    SharedPreferences sharedPre=context.getSharedPreferences("user", context.MODE_PRIVATE);
                    //获取Editor对象
                    SharedPreferences.Editor editor=sharedPre.edit();
                    //设置参数
                    editor.putString("username", user);
                    editor.putString("password", pwd);
                    //提交
                    editor.commit();

                    //如果客户不存在在SQLite数据库中就存进去
                    CustomerHelper customerHelper = new CustomerHelper();
                    if(!customerHelper.hasCustomer(user))
                    {
                        customerHelper.insert(user);
                    }
                    customerHelper.closeDB();

                    //跳转到首页
                    Intent intent = new Intent(login.this, ShouyeDemo.class);
                    startActivity(intent);
                    finish();
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);



        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,login.this.getApplicationContext());

        register=(TextView)findViewById(R.id.go_register);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        remember=(CheckBox)findViewById(R.id.remember);

        sp = getSharedPreferences("msg",MODE_PRIVATE);//设置SharedPreferences对象
        String user = sp.getString("username","");
        username.setText(user);
        String pass = sp.getString("password","");
        password.setText(pass);

        //注册监听，跳转到注册界面
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent
                Intent  int1 = new Intent(login.this, Register.class);
                startActivityForResult(int1, 1);
            }
        });
    }


    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    //处理注册界面返回值
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==2){
                String str = data.getExtras().getString("result");
                if(str!=null) {
                    username.setText(str.toCharArray(), 0, str.length());
                }
            }
        }
    }


    //点击登录按钮，向后台发送请求
    public void go(View view){
        String str=username.getText().toString();
        String pwd=password.getText().toString();
        //FormBody.Builder builder1=new FormBody.Builder();
        //FormBody formBody=builder1.add("username",str).add("password",pwd).build();
       // Request.Builder builder=new Request.Builder();
        Request request =new Request.Builder().url("http://192.168.40.14:8080/dgManager/userlogin!userLogin?username="+str+"&password="+pwd)
                .get()
                .build();
        //Request request=builder.url("http://192.168.40.14:8080/dgManager/userlogin").post(formBody).build();
        exec(request);


        /*Intent intent=new Intent(login.this,MainActivity.class);
        intent.putExtra("username",str);
        startActivity(intent);
        this.onPause();*/
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

    protected void onPause(){
        super.onPause();
        Log.i("info","LoginPause");
    }

    protected void onStop(){
        super.onStop();
        Log.i("info","LoginStop");
        if(remember.isChecked()) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username",username.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.commit();
        }
        else{
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username","");
            editor.putString("password","");
            editor.commit();
        }
    }


    //弹出popwindow
    public void show(View view){

        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(login.this,"all", mIUiListener);


//        new AlertDialog.Builder(login.this).setTitle("正在授权登录")
//                .setView(LayoutInflater.from(login.this).inflate(R.layout.oauthing,null))
//                .create().show();
//        //3秒后跳转
//        new Thread(){
//            public void run(){
//                super.run();
//                try{
//                    sleep(3000);
//                }catch(InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
//
//        }.start();


    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(login.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new AlertDialog.Builder(login.this).setTitle("正在授权登录")
                    .setView(LayoutInflater.from(login.this).inflate(R.layout.oauthing,null))
                    .create().show();
            //3秒后跳转
            new Thread(){
                public void run(){
                    super.run();
                    try{
                        sleep(3000);
                        Intent intent=new Intent(login.this,ShouyeDemo.class);
                        startActivity(intent);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }.start();

        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(login.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(login.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }




}
