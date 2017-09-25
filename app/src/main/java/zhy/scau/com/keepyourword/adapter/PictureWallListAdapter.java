package zhy.scau.com.keepyourword.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import zhy.scau.com.keepyourword.R;
import zhy.scau.com.keepyourword.bean.ImageBean;
import zhy.scau.com.keepyourword.imageloader.ImageLoader;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
/**
 * Created by ZhengHy on 2017-09-18.
 */

public class PictureWallListAdapter extends RecyclerView.Adapter<PictureWallListAdapter.ViewHolder> {

    private Context ctx;

    private List<ImageBean> data;

    private List<Integer> mSelectedIndexs;


    public PictureWallListAdapter(Context context, List<ImageBean> list){
        ctx = context;
        data = list;
        mSelectedIndexs = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View view = layoutInflater.inflate(R.layout.item_recyleview_picture,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position) {
        holder.index = position;
        final Integer positionI = Integer.valueOf(position);

        if(mSelectedIndexs.contains(positionI)){
            holder.ivSelected.setImageResource(R.mipmap.selected);
        }else{
            holder.ivSelected.setImageResource(R.mipmap.unselected);
        }

        holder.ivSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSelectedIndexs.contains(positionI)){
                    mSelectedIndexs.remove(positionI);
                    holder.ivSelected.setImageResource(R.mipmap.unselected);
                }else if(mSelectedIndexs.size() > 8 ){
                    //TODO 提醒选择上限


                }else{
                    mSelectedIndexs.add(positionI);
                    holder.ivSelected.setImageResource(R.mipmap.selected);
                }
            }
        });
        RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .centerCrop()
            .override(360,360)
            .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(ctx).load(data.get(position).getPath()).apply(requestOptions).transition(withCrossFade()).into(holder.ivPic);


    }



    @Override
    public int getItemCount() {
        int count = data == null? 0 : data.size();
        return count;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivPic;

        public ImageView ivSelected;

        public int index;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.item_imageview);
            ivSelected = itemView.findViewById(R.id.item_status);
            ivSelected.setImageResource(R.mipmap.unselected);
        }
    }
}
