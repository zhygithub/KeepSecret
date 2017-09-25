package zhy.scau.com.keepyourword.frame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by ZhengHy on 2017-09-14.
 */

public abstract class AbstractView<T extends AbstractPresenter> implements IBaseContract.IBaseView{

    private Activity mActivity;

    private T mPresenter;

    public AbstractView(Activity activity){
        this.mActivity = activity;
        mPresenter = createPresenter(mActivity);
    }

    public Activity getActivity(){
        return mActivity;
    }

    public abstract T createPresenter(Context context);

    protected T getPresenter(){
        return  mPresenter;
    }

    protected abstract void onCreate(Bundle savedInstanceState, View rootView);

    protected  void onStart(){}

    protected  void onResume(){}

    protected  void onRestart(){}

    protected  void onPause(){}

    protected  void onStop(){}

    protected  void onDestroy(){
        if(mPresenter != null){
            mPresenter.onDestroy();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mPresenter != null){
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }
}
