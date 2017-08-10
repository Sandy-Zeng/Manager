package chinasoft.com.logindemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.name;

@ContentView(R.layout.activity_register)
public class Register extends Activity {
    private Button register;
    private Dialog dialog;
    @ViewInject(R.id.regisusername)
    private EditText username;
    @ViewInject(R.id.regispassword)
    private EditText password;
    @ViewInject(R.id.address)
    private EditText address;
    @ViewInject(R.id.phone)
    private EditText phone;
    @ViewInject(R.id.repassword)
    private EditText repassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        x.view().inject(this);

        register=(Button)findViewById(R.id.regisbutton);
        register.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String user = username.getText().toString();
                    String pwd = password.getText().toString();
                    String add = address.getText().toString();
                    String pho = phone.getText().toString();
                    String repwd = repassword.getText().toString();
                    if (user.equals("") || pwd.equals("") || add.equals("") || pho.equals("") || repwd.equals("")) {
                        dialog = new AlertDialog.Builder(Register.this).setTitle("请完善用户信息！")
                                .create();
                        dialog.show();
                    } else {
                        if (!pwd.equals(repwd)) {
                            dialog = new AlertDialog.Builder(Register.this).setTitle("手抖了吗？密码输入不一致，请重新输入")
                                    .create();
                            dialog.show();
                        } else {
                            Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/userlogin!registerUser?username=" + user + "&password=" + pwd +
                                    "&address=" + add + "&phone=" + pho)
                                    .get()
                                    .build();
                            exec(request);
                        }
                    }

                }
        });
    }

    private void exec(Request request) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", "链接失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("info", "链接成功");
                String s = response.body().string();
                Message message = new Message();
                message.what = 1;
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (msg.what == 1) {
                //String result="ok";
                Log.i("info", result);
                if (result.equals("注册成功")) {
                    Intent intent = new Intent();
                    intent.putExtra("result", name);
                    setResult(2, intent);
                    finish();
                    Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                } else {
                    dialog = new AlertDialog.Builder(Register.this).setTitle(result)
                            .create();
                    dialog.show();
                }
            }
        }

    };

    public void back(View v){
        Intent intent=new Intent(Register.this,login.class);
        startActivity(intent);
        finish();
    }

}
