package zhy.scau.com.keepyourword.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import zhy.scau.com.keepyourword.R;
import zhy.scau.com.keepyourword.frame.AbstractView;

/**
 * Created by ZhengHy on 2017-09-15.
 */

public class TestView extends AbstractView<TestPresenter> implements ITestContract.ITestView {


    private Button btnCamera;

    private Button btnPhotos;

    private Uri uri;

    public TestView(Activity activity) {
        super(activity);
    }

    @Override
    public void show(String data) {

    }

    @Override
    public TestPresenter createPresenter(Context context) {
        return new TestPresenter(context, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, View rootView) {
        btnCamera = rootView.findViewById(R.id.btn_camera);
        btnPhotos = rootView.findViewById(R.id.btn_photos);





        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().callCamera();
            }
        });
    }


}
