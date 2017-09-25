package zhy.scau.com.keepyourword.frame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import zhy.scau.com.keepyourword.eventbus.ILoginStatusSubscriber;
import zhy.scau.com.keepyourword.eventbus.IRegistable;
import zhy.scau.com.keepyourword.eventbus.events.LoginEvent;
import zhy.scau.com.keepyourword.eventbus.events.LogoutEvent;

/**
 * Created by ZhengHy on 2017-09-14.
 */

public abstract class AbstractPresenter<T extends AbstractView> implements IBaseContract.IBasePresenter,IRegistable,ILoginStatusSubscriber{

    private Context mContext;

    private T mAbstractView;

    public AbstractPresenter(Context context, T abstractView){
        this.mContext = context;
        this.mAbstractView = abstractView;
    }

    public Intent getIntent(){
        return  mAbstractView.getActivity().getIntent();
    }

    public T getView(){
        return mAbstractView;
    }

    public Context getContext(){
        return  mContext;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }


    public void onSaveInstanceState(Bundle outState) {
    }

    public void onDestroy(){

    }

}
