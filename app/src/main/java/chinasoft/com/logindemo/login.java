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

    //处理注册界面返回值
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
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
}
