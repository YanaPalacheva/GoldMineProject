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

import appdb.Group;
import io.realm.Realm;

/**
 * Created by 46 on 21.12.2017.
 */

public class GroupAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<Group> mData;
    private Realm realm;

    public GroupAdapter(Context context, List data) {
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
        realm = ((MainActivity) context).getRealm();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Group getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View vi = view;             //trying to reuse a recycled view
        GroupAdapter.ViewHolder holder = null;
        if (vi == null) {
            //The view is not a recycled one: we have to inflate
            vi = mLayoutInflater.inflate(R.layout.item_profile, parent, false);
            holder = new GroupAdapter.ViewHolder();
            holder.tvName = vi.findViewById(R.id.tvTextGroup);
            holder.ivImage = vi.findViewById(R.id.ivImageGroup);
            holder.tvTotal = vi.findViewById(R.id.tvTotalGroup);
            vi.setTag(holder);
        } else {
            // View recycled !
            // no need to inflate
            // no need to findViews by id
            holder = (GroupAdapter.ViewHolder) vi.getTag();
        }
        Group item = getItem(position);
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
