package zhy.scau.com.keepyourword.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import zhy.scau.com.keepyourword.R;
import zhy.scau.com.keepyourword.eventbus.events.TestEvent;
import zhy.scau.com.keepyourword.frame.AbstractView;

/**
 * Created by ZhengHy on 2017-09-15.
 */

public class TestView extends AbstractView<TestPresenter> implements ITestContract.ITestView {

    private TextView mTvTest;

    public TestView(Activity activity) {
        super(activity);
    }

    @Override
    public void show(String data) {
        mTvTest.setText(data);
    }

    @Override
    public TestPresenter createPresenter(Context context) {
        return new TestPresenter(context, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, View rootView) {
        mTvTest = rootView.findViewById(R.id.tv_test);

        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestEvent testEvent = new TestEvent();
                testEvent.data = "yyrt";
                EventBus.getDefault().post(testEvent);
            }
        });
    }
}
