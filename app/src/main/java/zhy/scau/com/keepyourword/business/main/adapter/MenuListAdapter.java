package zhy.scau.com.keepyourword.business.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import zhy.scau.com.keepyourword.R;

/**
 * Created by ZhengHy on 2017-08-31.
 */

public class MenuListAdapter extends BaseAdapter {

    public String[] mDatas;

    public Context mCtx;

    public MenuListAdapter(Context ctx, String[] datas){
        this.mCtx = ctx;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas == null? 0 : mDatas.length;
    }

    @Override
    public Object getItem(int i) {
        return mDatas[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mCtx).inflate(R.layout.listview_item_menu, null);
        ((TextView)view.findViewById(R.id.tv_menu_item)).setText(mDatas[i]);
        return view;
    }
}
