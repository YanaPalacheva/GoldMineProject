package com.example.www.goldmineproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import appdb.User;
import io.realm.Realm;

/**
 * Created by www on 20.12.2017.
 */

public class MyAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<User> mData;
    private Realm realm = Realm.getDefaultInstance();
    public MyAdapter(Context context, List data){
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }
    @Override
    public User getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View vi = view;             //trying to reuse a recycled view
        ViewHolder holder = null;
        if (vi == null) {
            //The view is not a recycled one: we have to inflate
            vi = mLayoutInflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.tvName = vi.findViewById(R.id.tvText);
            holder.ivImage = vi.findViewById(R.id.ivImage);
            vi.setTag(holder);
        } else {
            // View recycled !
            // no need to inflate
            // no need to findViews by id
            holder = (ViewHolder) vi.getTag();
        }
        User item = getItem(position);
        holder.tvName.setText(item.getName());
        holder.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(item.getPic(), 0, item.getPic().length));
        return vi;
    }


    static class ViewHolder{
        TextView tvName;
        ImageView ivImage;
    }
}
