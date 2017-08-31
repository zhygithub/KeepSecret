package zhy.scau.com.keepyourword.popupwindow;

import android.view.View;

/**
 * Created by 80234812 on 2017/7/28.
 *
 * popupwindow 展示平台的接口定义
 */
public interface IPoupPlatform {

    /**
     * 设置展示内容
     * @param view
     */
    void setView(View view);

    /**
     * 设置最大高度
     * @param height
     */
    void setMaxHeight(int height);

    /**
     * 设置依附的对象
     * @param view
     */
    void setLocation(View view);

    /**
     * 展示
     */
    void show();

    /**
     * 隐藏
     */
    void hide();

}
