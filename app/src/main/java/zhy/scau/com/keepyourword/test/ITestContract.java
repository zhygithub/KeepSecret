package zhy.scau.com.keepyourword.test;

import zhy.scau.com.keepyourword.frame.IBaseContract;

/**
 * Created by ZhengHy on 2017-09-15.
 */

public interface ITestContract extends IBaseContract {

    interface ITestView extends IBaseContract.IBaseView{
        void show(String data);
    }

    interface ITestPresenter extends IBasePresenter{
        void refreshData();
    }
}
