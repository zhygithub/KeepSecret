package zhy.scau.com.keepyourword.test;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import zhy.scau.com.keepyourword.eventbus.events.TestEvent;
import zhy.scau.com.keepyourword.eventbus.events.LoginEvent;
import zhy.scau.com.keepyourword.eventbus.events.LogoutEvent;
import zhy.scau.com.keepyourword.frame.AbstractPresenter;

/**
 * Created by ZhengHy on 2017-09-15.
 */

public class TestPresenter extends AbstractPresenter<TestView> implements ITestContract.ITestPresenter {

    private int mClickCount = 0 ;

    public TestPresenter(Context context, TestView abstractView) {
        super(context, abstractView);
    }

    @Override
    public void refreshData() {
        getView().show("test success : " + mClickCount++);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTestEvent(TestEvent event){
        getView().show(event.data + mClickCount++);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLogin(LoginEvent loginEvent) {

    }

    @Override
    public void onLogout(LogoutEvent logoutEvent) {

    }
}
