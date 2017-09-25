package zhy.scau.com.keepyourword.eventbus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import zhy.scau.com.keepyourword.eventbus.events.LoginEvent;
import zhy.scau.com.keepyourword.eventbus.events.LogoutEvent;

/**
 * Created by ZhengHy on 2017-09-18.
 */

public interface ILoginStatusSubscriber extends ISubscriber{

    @Subscribe(threadMode = ThreadMode.POSTING)
    void onLogin(LoginEvent loginEvent);

    @Subscribe(threadMode = ThreadMode.POSTING)
    void onLogout(LogoutEvent logoutEvent);
}
