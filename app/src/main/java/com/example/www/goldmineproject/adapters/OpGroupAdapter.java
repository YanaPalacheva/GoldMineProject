package com.example.www.goldmineproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.www.goldmineproject.R;

import java.util.List;

import appdb.Group;
import appdb.GroupOp;
import appdb.UserOp;
import io.realm.Realm;

/**
 * Created by Либро on 21.02.2018.
 */

public class OpGroupAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<GroupOp> mData;
    private Realm realm;
    private Context context;

    public OpGroupAdapter(Context context, List data){
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
    public GroupOp getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View vi = view;             //trying to reuse a recycled view
        OpGroupAdapter.ViewHolder holder = null;
        if (vi == null) {
            //The view is not a recycled one: we have to inflate
            vi = mLayoutInflater.inflate(R.layout.item_group_op, parent, false);
            holder = new OpGroupAdapter.ViewHolder();
            holder.tvSource=vi.findViewById(R.id.tvOpSource);
            holder.tvTarget=vi.findViewById(R.id.tvOpTarget);
            holder.tvComment = vi.findViewById(R.id.tvOpGroupComment);
            holder.tvTotal = vi.findViewById(R.id.tvOpGroupValue);
            holder.tvCurrency = vi.findViewById(R.id.tvOpGroupCurrency);
            vi.setTag(holder);
        } else {
            holder = (OpGroupAdapter.ViewHolder) vi.getTag();
        }
        GroupOp item = getItem(position);
        holder.tvSource.setText(item.getSource().getName());
        holder.tvTarget.setText(item.getTarget().getName());
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
        TextView tvSource;
        TextView tvTarget;
        TextView tvComment;
        TextView tvTotal;
        TextView tvCurrency;
    }
}
