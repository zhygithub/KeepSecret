package zhy.scau.com.keepyourword.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by 80234812 on 2017/7/28.
 */
public final class BaseUtils {

    /**
     * 判断一个对象是否是空引用
     *
     * @param o
     * @return
     */
    public static boolean isNull(Object o) {
        return o == null;
    }


    /**
     * 获得ListView的最大高度
     * @param listView
     * @return
     */
    public static int getListViewTotalHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }
        totalHeight = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        return totalHeight;
    }

    /**
     * 获得ListView的宽度
     * @param listView
     * @return
     */
    public static int getListViewTotalWidth(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalWidth = 0;

        if(listAdapter.getCount() > 0){
            View listItem = listAdapter.getView(0, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalWidth =  listItem.getMeasuredWidth();
        }

        return totalWidth;
    }

    /**
     * 设置ListView为最大高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        int totalHeight = getListViewTotalHeight(listView);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void tip(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("朕已知晓，退下把！", null).create().show();
    }
}

