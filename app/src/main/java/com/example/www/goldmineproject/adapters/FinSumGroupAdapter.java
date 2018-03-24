package com.example.www.goldmineproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.www.goldmineproject.R;

import java.util.List;

import appdb.CurTotal;
import appdb.CurTotalGroup;
import io.realm.Realm;

/**
 * Created by Либро on 21.02.2018.
 */

public class FinSumGroupAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<CurTotalGroup> mData;
    private Realm realm;
    private Context context;

    public FinSumGroupAdapter(Context context, List data){
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
    public CurTotalGroup getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View vi = view;             //trying to reuse a recycled view
        FinSumGroupAdapter.ViewHolder holder = null;
        if (vi == null) {
            //The view is not a recycled one: we have to inflate
            vi = mLayoutInflater.inflate(R.layout.item_group_fin_sum, parent, false);
            holder = new FinSumGroupAdapter.ViewHolder();
            holder.tvSource=vi.findViewById(R.id.tvOpSourceFin);
            holder.tvTarget=vi.findViewById(R.id.tvOpTargetFin);
            holder.tvSum = vi.findViewById(R.id.tvOpGroupValueFin);
            holder.tvCurrency = vi.findViewById(R.id.tvOpGroupCurrencyFin);
            vi.setTag(holder);
        } else {
            holder = (FinSumGroupAdapter.ViewHolder) vi.getTag();
        }
        CurTotalGroup item = getItem(position);

        holder.tvCurrency.setText(item.getCurrency().getName());
        //if (item.getTotal() != null) {
        String tot = String.valueOf(item.getValue());
        holder.tvSum.setText(tot);
        if (item.getValue()<0)
            holder.tvSum.setTextColor(context.getResources().getColor(R.color.minusBalance));
        else
            holder.tvSum.setTextColor(context.getResources().getColor(R.color.plusBalance));
        holder.tvSource.setText(item.getSource().getName());
        holder.tvTarget.setText(item.getTarget().getName());
        //}
        return vi;
    }

    static class ViewHolder{
        TextView tvSource;
        TextView tvTarget;
        TextView tvSum;
        TextView tvCurrency;
    }
}
