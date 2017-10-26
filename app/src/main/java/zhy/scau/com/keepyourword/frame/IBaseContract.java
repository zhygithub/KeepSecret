package zhy.scau.com.keepyourword.frame;

import android.content.Intent;

/**
 * Created by ZhengHy on 2017-09-14.
 */

public interface IBaseContract {

    interface  IBasePresenter{

        void startActivity(Intent intent);

        void startActivityForResult(Intent intent, int requestCode);

    }

    interface IBaseView{

    }
}
