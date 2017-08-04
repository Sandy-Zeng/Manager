package chinasoft.com.logindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class Loding extends Activity {
    private final long SPLASH_LENGTH = 3000;//延时3秒
    private Handler handler=new Handler();
    private TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        skip=(TextView) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Loding.this,login.class);
                startActivity(intent);
                finish();
                Loding.this.onDestroy();
            }
        });
        handler.postDelayed(new Runnable() {//延时跳转
            @Override
            public void run() {
                Intent intent=new Intent(Loding.this,login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_LENGTH);

        //用Timer类设置延时
        /*Timer timer=new Timer();//設置Timer类
        TimerTask task=new TimerTask() {//设置TimerTask 重写run方法
            @Override
            public void run() {
                Intent intent=new Intent(Loding.this,login.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task,3000);//设置延时*/

        //通过子线程
        /*new Thread(){
            public void run(){
                super.run();
                try {
                    Thread.sleep(3000);
                    Intent intent=new Intent(Loding.this,login.class);
                    startActivity(intent);
                    finish();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();*/

    }
}
