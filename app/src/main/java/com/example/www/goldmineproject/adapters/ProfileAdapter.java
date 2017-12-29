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
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by www on 20.12.2017.
 */

public class ProfileAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<User> mData;
    private Realm realm;
    public ProfileAdapter(Context context, List data, Realm realm){
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
        //realm = ((MainActivity)context).getRealm();
        this.realm = realm;
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
            vi = mLayoutInflater.inflate(R.layout.item_profile, parent, false);
            holder = new ViewHolder();
            holder.tvName = vi.findViewById(R.id.tvTextProfile);
            holder.ivImage = vi.findViewById(R.id.ivImageProfile);
            vi.setTag(holder);
        } else {
            // View recycled !
            // no need to inflate
            // no need to findViews by id
            holder = (ViewHolder) vi.getTag();
        }
        User item = getItem(position);
        holder.tvName.setText(item.getName());
        if (item.getPic() != null) {
            holder.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(item.getPic(), 0, item.getPic().length));
        }
        return vi;
    }


    static class ViewHolder{
        TextView tvName;
        CircleImageView ivImage;
    }
}
