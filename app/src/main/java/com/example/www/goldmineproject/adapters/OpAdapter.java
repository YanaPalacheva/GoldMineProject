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
import appdb.UserOp;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

/**
 * Created by Либро on 21.02.2018.
 */

public class OpAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<UserOp> mData;
    private Realm realm;
    private Context context;

    public OpAdapter(Context context, List data){
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
        //realm = ((MainActivity) context).getRealm();
        this.context = context;
    }
    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }
    @Override
    public UserOp getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View vi = view;             //trying to reuse a recycled view
        OpAdapter.ViewHolder holder = null;
        if (vi == null) {
            //The view is not a recycled one: we have to inflate
            vi = mLayoutInflater.inflate(R.layout.item_op, parent, false);
            holder = new OpAdapter.ViewHolder();
            holder.tvComment = vi.findViewById(R.id.tvOpComment);
            holder.tvTotal = vi.findViewById(R.id.tvOpValue);
            holder.tvCurrency = vi.findViewById(R.id.ivImageProfile);
            vi.setTag(holder);
        } else {
            holder = (OpAdapter.ViewHolder) vi.getTag();
        }
        UserOp item = getItem(position);
        holder.tvComment.setText(item.getCommentary());
        holder.tvCurrency.setText(item.getCurrency().getName());
        //if (item.getTotal() != null) {
        String tot = String.valueOf(item.getValue());
        holder.tvTotal.setText(tot);
        if (item.getValue()<0)
            holder.tvTotal.setTextColor(context.getResources().getColor(R.color.minusBalance));
        else
            holder.tvTotal.setTextColor(context.getResources().getColor(R.color.plusBalance));
        //}
        return vi;
    }


    static class ViewHolder{
        TextView tvComment;
        TextView tvTotal;
        TextView tvCurrency;
    }
}
