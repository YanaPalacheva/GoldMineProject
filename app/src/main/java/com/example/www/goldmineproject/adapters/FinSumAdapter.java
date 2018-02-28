package com.example.www.goldmineproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.www.goldmineproject.MainActivity;
import com.example.www.goldmineproject.R;

import java.util.List;

import appdb.CurTotal;
import io.realm.Realm;

/**
 * Created by Либро on 21.02.2018.
 */

public class FinSumAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<CurTotal> mData;
    private Realm realm;
    private Context context;

    public FinSumAdapter(Context context, List data){
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
    public CurTotal getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View vi = view;             //trying to reuse a recycled view
        FinSumAdapter.ViewHolder holder = null;
        if (vi == null) {
            //The view is not a recycled one: we have to inflate
            vi = mLayoutInflater.inflate(R.layout.item_op, parent, false);
            holder = new FinSumAdapter.ViewHolder();
            holder.tvSum = vi.findViewById(R.id.tvSumValue);
            holder.tvCurrency = vi.findViewById(R.id.tvSumCurrency);
            vi.setTag(holder);
        } else {
            holder = (FinSumAdapter.ViewHolder) vi.getTag();
        }
        CurTotal item = getItem(position);
        holder.tvSum.setText(String.valueOf(item.getValue()));
        holder.tvCurrency.setText(item.getCurrency().getName());
        //if (item.getTotal() != null) {
        String tot = String.valueOf(item.getValue());
        holder.tvSum.setText(tot);
        if (item.getValue()<0)
            holder.tvSum.setTextColor(context.getResources().getColor(R.color.minusBalance));
        else
            holder.tvSum.setTextColor(context.getResources().getColor(R.color.plusBalance));
        //}
        return vi;
    }

    static class ViewHolder{
        TextView tvSum;
        TextView tvCurrency;
    }
}
