package chinasoft.com.logindemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.fragment_test)
public class ShouyeDemo extends Activity {

    @ViewInject(R.id.shouye)
    private TextView shouye;
    @ViewInject(R.id.shouyeView)
    private ImageView shouyeView;
    @ViewInject(R.id.cart)
    private TextView cart;
    @ViewInject(R.id.cartView)
    private ImageView cartView;
    @ViewInject(R.id.my)
    private TextView my;
    @ViewInject(R.id.myView)
    private ImageView myView;
    @ViewInject(R.id.blogView)
    private ImageView blogView;
    @ViewInject(R.id.blog)
    private TextView blog;

    private Fragment contentFragment;
    private android.app.FragmentManager fragmentManager;
    private android.app.FragmentTransaction transaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test);
        x.view().inject(this);
        initUi();
    }

    private void initUi(){
        //初始化管理者
        fragmentManager = getFragmentManager();
        transaction =fragmentManager.beginTransaction();
        Fragment init=new ShouyeFragment();
        transaction.replace(R.id.fragmentPage,init,"fragment");
        transaction.commit();
        //shouye.setBackgroundColor(getResources().getColor(R.color.colorLogin));
        shouyeView.setImageResource(R.drawable.shouye_press);
        shouye.setTextColor(getResources().getColor(R.color.colorLogin));
    }


    //设置事件监听
    @Event(value={R.id.shouye,R.id.cart,R.id.blog,R.id.my,R.id.shouyeView,R.id.cartView,R.id.blogView,R.id.myView},type = View.OnClickListener.class)
    private void doEvent(View view){
        shouye.setBackgroundColor(0xffffffff);
        shouye.setTextColor(getResources().getColor(R.color.colorNormal));
        shouyeView.setImageResource(R.drawable.shouye_normal);
        cart.setBackgroundColor(0xffffffff);
        cart.setTextColor(getResources().getColor(R.color.colorNormal));
        cartView.setImageResource(R.drawable.cart_normal);
        blog.setBackgroundColor(0xffffffff);
        blog.setTextColor(getResources().getColor(R.color.colorNormal));
        blogView.setImageResource(R.drawable.blog_normal);
        my.setBackgroundColor(0xffffffff);
        my.setTextColor(getResources().getColor(R.color.colorNormal));
        myView.setImageResource(R.drawable.my_normal);



        transaction=fragmentManager.beginTransaction();
        switch(view.getId()){
            case R.id.shouye:
                contentFragment = new ShouyeFragment();
                transaction.replace(R.id.fragmentPage,contentFragment);
                //shouye.setBackgroundColor(getResources().getColor(R.color.colorLogin));
                shouye.setTextColor(getResources().getColor(R.color.colorLogin));
                shouyeView.setImageResource(R.drawable.shouye_press);
                break;
            case R.id.shouyeView:
                contentFragment = new ShouyeFragment();
                transaction.replace(R.id.fragmentPage,contentFragment);
                //shouye.setBackgroundColor(getResources().getColor(R.color.colorLogin));
                shouye.setTextColor(getResources().getColor(R.color.colorLogin));
                shouyeView.setImageResource(R.drawable.shouye_press);
                break;
            case R.id.blog:
                contentFragment = new BlogFragment();
                transaction.replace(R.id.fragmentPage,contentFragment);
                //blog.setBackgroundColor(getResources().getColor(R.color.colorLogin));
                blog.setTextColor(getResources().getColor(R.color.colorLogin));
                blogView.setImageResource(R.drawable.blog_press);
                break;
            case R.id.blogView:
                contentFragment = new BlogFragment();
                transaction.replace(R.id.fragmentPage,contentFragment);
                //blog.setBackgroundColor(getResources().getColor(R.color.colorLogin));
                blog.setTextColor(getResources().getColor(R.color.colorLogin));
                blogView.setImageResource(R.drawable.blog_press);
                break;

            case R.id.cart:
                contentFragment = new CartFragment();
                transaction.replace(R.id.fragmentPage,contentFragment);
                //cart.setBackgroundColor(getResources().getColor(R.color.colorLogin));
                cart.setTextColor(getResources().getColor(R.color.colorLogin));
                cartView.setImageResource(R.drawable.cart_press);
                break;
            case R.id.cartView:
                contentFragment = new CartFragment();
                transaction.replace(R.id.fragmentPage,contentFragment);
                //cart.setBackgroundColor(getResources().getColor(R.color.colorLogin));
                cart.setTextColor(getResources().getColor(R.color.colorLogin));
                cartView.setImageResource(R.drawable.cart_press);
                break;
            case R.id.my:
                contentFragment = new MyFragment();
                transaction.replace(R.id.fragmentPage,contentFragment);
                //my.setBackgroundColor(getResources().getColor(R.color.colorLogin));
                my.setTextColor(getResources().getColor(R.color.colorLogin));
                myView.setImageResource(R.drawable.my_press);
                break;
            case R.id.myView:
                contentFragment = new MyFragment();
                transaction.replace(R.id.fragmentPage,contentFragment);
                //my.setBackgroundColor(getResources().getColor(R.color.colorLogin));
                my.setTextColor(getResources().getColor(R.color.colorLogin));
                myView.setImageResource(R.drawable.my_press);
                break;
            default:
                break;
        }
        transaction.commit();

    }




}
