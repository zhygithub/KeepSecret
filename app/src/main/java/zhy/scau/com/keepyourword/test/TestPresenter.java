package zhy.scau.com.keepyourword.test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import zhy.scau.com.keepyourword.eventbus.events.TestEvent;
import zhy.scau.com.keepyourword.eventbus.events.LoginEvent;
import zhy.scau.com.keepyourword.eventbus.events.LogoutEvent;
import zhy.scau.com.keepyourword.frame.AbstractPresenter;

/**
 * Created by ZhengHy on 2017-09-15.
 */

public class TestPresenter extends AbstractPresenter<TestView> implements ITestContract.ITestPresenter {

    private int mClickCount = 0 ;

    private Uri uri;

    private File file;

    public TestPresenter(Context context, TestView abstractView) {
        super(context, abstractView);

        File cacheDir = Environment.getExternalStorageDirectory();
        file = new File(cacheDir, "user-avatar.jpg");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            //通过FileProvider创建一个content类型的Uri(android 7.0需要这样的方法访问)
            uri = FileProvider.getUriForFile(getContext(), "zhy.scau.com.fileprovider", file);//使用在manifest里面设置的包名
        }
    }

    @Override
    public void refreshData() {
        getView().show("test success : " + mClickCount++);
    }

    @Override
    public void callCamera() {
        if(checkCameraPermission()){
            callCamera(uri);
        }
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

    private boolean checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // 摄像头权限还未得到用户的同意

            // 摄像头还没有被拒绝，直接申请
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA);


        } else {
            // 摄像头权限以及有效，显示摄像头预览界面
            return true;
        }

        return false;
    }

    public static final int REQUEST_CAMERA = 1001;

    public static final int REQUEST_CODE_TAKE_PHOTO = 10001;

    public static final int REQUEST_CODE_CROUP_PHOTO = 10002;

    private void callCamera(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity)getContext()).startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA){
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 摄像头权限已经申请成功，可以展示摄像预览界面了
                callCamera(uri);
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_TAKE_PHOTO){
            //调用系统裁剪方法进行裁剪
            if(resultCode == RESULT_OK){
                startPhotoZoom(uri);
            }
        }else if (requestCode == REQUEST_CODE_CROUP_PHOTO) {
            //获取图片路径进行设置
            if(resultCode == RESULT_OK){
                compressAndUploadAvatar(file.getPath());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void compressAndUploadAvatar(String path) {

    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        // intent.putExtra("outputX", Constants.USER_AVATAR_MAX_SIZE);//图片输出大小,可以不需要
        //intent.putExtra("outputY", Constants.USER_AVATAR_MAX_SIZE);
        //注意这里的输出的是上面的文件路径的Uri格式，这样在才能获取图片
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        ((Activity)getContext()).startActivityForResult(intent, REQUEST_CODE_CROUP_PHOTO);

    }
}
