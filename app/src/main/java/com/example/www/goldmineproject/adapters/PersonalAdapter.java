package com.example.www.goldmineproject.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.www.goldmineproject.MainActivity;
import com.example.www.goldmineproject.R;

import java.util.List;

import appdb.User;
import io.realm.Realm;

/**
 * Created by 46 on 21.12.2017.
 */

public class PersonalAdapter  extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<User> mData;
    private Realm realm;

    public PersonalAdapter(Context context, List data) {
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
        realm = ((MainActivity) context).getRealm();
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
        PersonalAdapter.ViewHolder holder = null;
        if (vi == null) {
            //The view is not a recycled one: we have to inflate
            vi = mLayoutInflater.inflate(R.layout.item_profile, parent, false);
            holder = new PersonalAdapter.ViewHolder();
            holder.tvName = vi.findViewById(R.id.tvText);
            holder.ivImage = vi.findViewById(R.id.ivImage);
            holder.tvTotal = vi.findViewById(R.id.tvTotal);
            vi.setTag(holder);
        } else {
            // View recycled !
            // no need to inflate
            // no need to findViews by id
            holder = (PersonalAdapter.ViewHolder) vi.getTag();
        }
        User item = getItem(position);
        holder.tvName.setText(item.getName());
        if (item.getTotal() != null) {
            holder.tvTotal.setText(item.getTotal().toString());
        }
        if (item.getPic() != null) {
            holder.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(item.getPic(), 0, item.getPic().length));
        }
        return vi;
    }


    static class ViewHolder {
        TextView tvName;
        ImageView ivImage;
        TextView tvTotal;
    }
}
