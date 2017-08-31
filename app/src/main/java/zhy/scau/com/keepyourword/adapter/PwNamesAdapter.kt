package zhy.scau.com.keepyourword.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import zhy.scau.com.keepyourword.R
import zhy.scau.com.keepyourword.db.PWBean

/**
 * Created by ZhengHy on 2017-08-31.
 */
class PwNamesAdapter() : BaseAdapter() {

    lateinit var mCtx : Context

    lateinit var mDatas : List<PWBean>

    var mCurSelect : Int = 0;

    constructor(ctx : Context ,datas : List<PWBean>) : this(){
        mCtx = ctx
        mDatas = datas
    }

    fun setSelectedPosi(posi: Int){
        mCurSelect = posi
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var viewHolder : ViewHolder?
        var view :View? = p1
        if(view == null){
            val layoutInflater = LayoutInflater.from(mCtx);
            view = layoutInflater.inflate(R.layout.listview_item_pwnames, null)
            viewHolder = ViewHolder()

            viewHolder.ivSelected = view.findViewById(R.id.iv_selected)
            viewHolder.tvPwName = view.findViewById(R.id.tv_pw_name)

            view.tag = viewHolder

        }else{
            viewHolder = view.tag as ViewHolder
        }

        if(viewHolder != null){
            viewHolder.ivSelected.visibility = View.INVISIBLE
            viewHolder.tvPwName.text = mDatas.get(p0).pwName

            if(p0 == mCurSelect){
                viewHolder.ivSelected.visibility = View.VISIBLE
            }
        }

        return view!!
    }

    override fun getItem(p0: Int): Any {
        return mDatas.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return mDatas?.size;
    }

    class ViewHolder{

        lateinit var ivSelected : ImageView

        lateinit var tvPwName : TextView
    }
}