package zhy.scau.com.keepyourword.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import zhy.scau.com.keepyourword.utils.BaseConstant;
import zhy.scau.com.keepyourword.utils.BaseUtils;


/**
 * Created by 80234812 on 2017/7/28.
 *
 * popupwindow 基类
 */
public class PoupWindow extends PopupWindow implements IPoupPlatform{

    /**
     * 该弹窗依附的对象
     */
    private View mLocaView;

    /**
     * 最大高度
     */
    private int mMaxHeight = BaseConstant.ILLAGEL_INT;


    @Override
    public void setView(View view) {
        if(BaseUtils.isNull(view)){
            return ;
        }

        this.setContentView(view);
        view.measure(0,0);
        int measuredHeight = view.getMeasuredHeight();
        if(mMaxHeight != BaseConstant.ILLAGEL_INT && measuredHeight > mMaxHeight){
            measuredHeight = mMaxHeight;
        }
        this.setHeight(measuredHeight);
        this.setWidth(view.getMeasuredWidth());
    }

    @Override
    public void setMaxHeight(int height) {
        mMaxHeight = height;

        View contentView = this.getContentView();

        if(!BaseUtils.isNull(contentView)){
            contentView.measure(0,0);
            int measuredHeight = contentView.getMeasuredHeight();
            if(mMaxHeight != BaseConstant.ILLAGEL_INT && measuredHeight > mMaxHeight){
                measuredHeight = mMaxHeight;
            }
            this.setHeight(measuredHeight);
        }
    }

    @Override
    public void setLocation(View view) {
        mLocaView = view;
    }

    @Override
    public void show() {
        if(!BaseUtils.isNull(mLocaView)){
            showAsDropDown(mLocaView);
        }
    }

    @Override
    public void hide() {
        dismiss();
    }

    public void setFullScreen(){
        setWidth(BaseConstant.WINDOW_WIDTH);
        setHeight(BaseConstant.WINDOW_HEIGHT);
    }

    public void showInCenter(){
        if(!BaseUtils.isNull(mLocaView)){
            showAtLocation(mLocaView, Gravity.CENTER,0,0);
        }
    }
}
