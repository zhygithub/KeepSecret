package zhy.scau.com.keepyourword.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;


import zhy.scau.com.keepyourword.eventbus.EventBusManager;
import zhy.scau.com.keepyourword.eventbus.IRegistable;

/**
 * Created by ZhengHy on 2017-09-15.
 */

public class FrameActivity<T extends AbstractView<AbstractPresenter>> extends MVPActivity<T> {

    @Override
    protected void initViewAndPresenter() {

    }

    @Override
    protected int getContentResId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(IRegistable registable : getPresenters())
        EventBusManager.getInstance().register(registable);
    }
}
