package zhy.scau.com.keepyourword.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhengHy on 2017-09-14.
 */

public abstract class MVPActivity<T extends AbstractView> extends BaseActivity {

    private List<T> mViews;

    private List<AbstractPresenter> mPresenters;

    public MVPActivity(){
        mViews = new ArrayList<>();
        mPresenters = new ArrayList<>();
        initViewAndPresenter();
    }

    protected List<AbstractPresenter> getPresenters(){
        return mPresenters;
    }

    protected abstract void initViewAndPresenter();

    protected abstract int getContentResId();

    public void addView(T view){
        mViews.add(view);
        mPresenters.add(view.getPresenter());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResId());

        for (T view: mViews) {
            view.onCreate(savedInstanceState, findViewById(android.R.id.content));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (T view: mViews) {
            view.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        for (T view: mViews) {
            view.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (T view: mViews) {
            view.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (T view: mViews) {
            view.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (T view: mViews) {
            view.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (T view: mViews) {
            view.onDestroy();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (T view: mViews) {
            view.getPresenter().onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (T view: mViews) {
            view.getPresenter().onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (T view: mViews) {
            view.getPresenter().onActivityResult(requestCode, resultCode, data);
        }
    }
}
