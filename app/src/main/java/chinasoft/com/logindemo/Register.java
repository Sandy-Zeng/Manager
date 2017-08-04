package chinasoft.com.logindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
    private EditText username;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        username=(EditText)findViewById(R.id.regisusername);
        register=(Button)findViewById(R.id.regisbutton);
        register.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final String name=username.getText().toString();
                    Intent intent=new Intent();
                    intent.putExtra("result",name);
                    setResult(2,intent);
                    finish();

                    /*MyHelper myHelper=new MyHelper(Register.this,"user.db",null,2);
                    SQLiteDatabase db=myHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();*/
                   /* cv.put("username",username.getText().toString());
                    cv.put("pwd","123");
                    db.insert("userinfo",null,cv);
                    cv.put("pwd","114");
                    db.update("userinfo",cv,"username=?",new String[]{"Sandy"});
                    db.update("userinfo",cv,"username = 'Sandy'",null);*/

                  /*  Cursor cursor=db.rawQuery("select * from userinfo where username =?",new String[]{"Sandy"});
                    while(cursor.moveToNext()){
                        String p=cursor.getString(cursor.getColumnIndex("pwd"));
                        Log.i("info","pwd"+p);
                    }*/


                    Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                }
        });
    }

    public void back(View v){
        Intent intent=new Intent(Register.this,login.class);
        startActivity(intent);
        finish();
    }

}
