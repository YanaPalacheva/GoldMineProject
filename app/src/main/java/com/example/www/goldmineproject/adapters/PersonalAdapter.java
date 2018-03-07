package com.example.www.goldmineproject.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.www.goldmineproject.MainActivity;
import com.example.www.goldmineproject.R;

import java.util.List;

import appdb.User;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

/**
 * Created by 46 on 21.12.2017.
 */

public class PersonalAdapter  extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<User> mData;
    private Realm realm;
    private Context context;

    public PersonalAdapter(Context context, List data) {
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
        realm = ((MainActivity) context).getRealm();
        this.context = context;
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
            vi = mLayoutInflater.inflate(R.layout.item_personal, parent, false);
            holder = new PersonalAdapter.ViewHolder();
            holder.tvName = vi.findViewById(R.id.tvTextPersonal);
            holder.ivImage = vi.findViewById(R.id.ivImagePersonal);
            holder.tvTotal = vi.findViewById(R.id.tvTotalPersonal);
            vi.setTag(holder);
        } else {
            holder = (PersonalAdapter.ViewHolder) vi.getTag();
        }
        User item = getItem(position);

            holder.tvName.setText(item.getName());
            //if (item.getTotal() != null) {
            String tot = "~" + String.valueOf(item.getTotal());
            holder.tvTotal.setText(tot);
            if (item.getTotal() < 0)
                holder.tvTotal.setTextColor(context.getResources().getColor(R.color.minusBalance));
            else
                holder.tvTotal.setTextColor(context.getResources().getColor(R.color.plusBalance));
            //}
            if (item.getPic() != null) {
                holder.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(item.getPic(), 0, item.getPic().length));
            }

        return vi;
    }

    static class ViewHolder {
        TextView tvName;
        CircleImageView ivImage;
        TextView tvTotal;
    }
}
