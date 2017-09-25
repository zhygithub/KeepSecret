package zhy.scau.com.keepyourword.test;

import zhy.scau.com.keepyourword.R;
import zhy.scau.com.keepyourword.frame.FrameActivity;

/**
 * Created by ZhengHy on 2017-09-14.
 */

public class TestActivity extends FrameActivity {

    @Override
    protected void initViewAndPresenter() {
        addView(new TestView(this));
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_test;
    }


}
