package zhy.scau.com.keepyourword.business.picwall;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import zhy.scau.com.keepyourword.R;
import zhy.scau.com.keepyourword.adapter.PictureWallListAdapter;
import zhy.scau.com.keepyourword.bean.ImageBean;
import zhy.scau.com.keepyourword.frame.FrameActivity;

public class PictureWallActivity extends FrameActivity {

    private int PERMISSION_REQUEST_CODE = 100001;

    private RecyclerView recyclerView ;

    private List<ImageBean> mImageBeanList;
    @Override
    protected void initViewAndPresenter() {
        super.initViewAndPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_picture_wall;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_wall);
        recyclerView = findViewById(R.id.rv_wall);
        checkPermissionAndLoadImages();
    }

    /**
     * 检查权限并加载SD卡里的图片。
     */
    private void checkPermissionAndLoadImages() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplication(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限，加载图片。
            loadImageForSDCard();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(PictureWallActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 处理权限申请的回调。
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，加载图片。
                loadImageForSDCard();
            } else {
                //拒绝权限，弹出提示框。
                showDialog("没有获得权限，无法使用该功能");
            }
        }
    }

    int i = 0;
    private void loadImageForSDCard() {
        loadImageForSDCard(this, new DataCallback() {
            @Override
            public void onSuccess(List<ImageBean> imageBeanList) {
                mImageBeanList = imageBeanList;
                handler.sendEmptyMessage(123);
            }
        });
    }

    public void performImage(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new PictureWallListAdapter(this,mImageBeanList));


    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tip").setMessage(message).setPositiveButton("明白",null);
        builder.show();
    }

    /**
     * 从SDCard加载图片
     */
    public void loadImageForSDCard(final Context context, final DataCallback callback) {
        //由于扫描图片是耗时的操作，所以要在子线程处理。
        new Thread(new Runnable() {
            @Override
            public void run() {
                //扫描图片
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();
                Cursor mCursor = mContentResolver.query(mImageUri, new String[]{
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media._ID},
                    null,
                    null,
                    MediaStore.Images.Media.DATE_ADDED);

                ArrayList<ImageBean> images = new ArrayList<>();
                //读取扫描到的图片
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    //获取图片名称
                    String name = mCursor.getString(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    //获取图片时间
                    long time = mCursor.getLong(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                    images.add(new ImageBean(path, time, name));
                }
                mCursor.close();
                Collections.reverse(images);

                callback.onSuccess(images);
            }
        }).start();
    }

    interface DataCallback{
        void onSuccess(List<ImageBean> imageBeanList);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 123){
                showDialog("加载成功，共" + mImageBeanList.size() + "张图片");
                performImage();
            }

        }
    };
}
