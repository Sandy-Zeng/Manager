package chinasoft.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import chinasoft.com.fragmentitem.FragmentItem1;
import chinasoft.com.fragmentitem.FragmentItem2;
import chinasoft.com.fragmentitem.FragmentItem3;
import chinasoft.com.service.Message;

/**
 * Created by lenovo on 2017/8/6.
 */

public class SlideFragmentPagerAdapter extends DragDetailFragmentPagerAdapter {
    private int image[];
    private String brandName;
    private String cn;
    private String de;
    private String hl;
    private String ty;
    private List<Message> message;

    public SlideFragmentPagerAdapter(FragmentManager fm, int images[]) {
        super(fm);
        image = images;
    }

    public void set(String b, String c, String h, String d, String t) {
        brandName = b;
        cn = c;
        hl = h;
        de = d;
        ty = t;

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            FragmentItem2 f = new FragmentItem2();
            f.setImage(image);
            return f;
        }
        if (position == 1) {
            FragmentItem3 f = new FragmentItem3();
            f.set(brandName, cn, hl, de, ty);
            return f;
        }
        FragmentItem1 f = new FragmentItem1();
        f.setMessage(message);
        return f;

    }

    @Override
    public int getCount() {
        return 3;
    }

    private View mCurrentView;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            mCurrentView = (View) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            mCurrentView = fragment.getView();
        }
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "商品详情";
        }
        else if(position==1){
            return "产品参数";
        }else if(position==2){
            return "宝贝评价";
        }else return"";
    }

    public void setMessage(List<Message> messageList) {
        message = messageList;
    }

}
