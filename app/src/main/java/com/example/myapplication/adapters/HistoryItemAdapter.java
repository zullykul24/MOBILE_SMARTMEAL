package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import com.example.myapplication.models.HistoryItem;

import java.util.List;

public class HistoryItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HistoryItem> historyItemList;

    private class ViewHolder {
        TextView orderId, tableId;
        TextView priceTotal;
        TextView paymentTime;
    }

    public HistoryItemAdapter(Context context, int layout, List<HistoryItem> historyItemList) {
        this.context = context;
        this.layout = layout;
        this.historyItemList = historyItemList;
    }

    @Override
    public int getCount() {
        return historyItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryItemAdapter.ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new HistoryItemAdapter.ViewHolder();
            holder.orderId = (TextView)convertView.findViewById(R.id.history_orderid);
            holder.tableId = (TextView) convertView.findViewById(R.id.history_tableid);
            holder.paymentTime = (TextView)convertView.findViewById(R.id.payment_time);
            holder.priceTotal = (TextView)convertView.findViewById(R.id.history_price_total);
            convertView.setTag(holder);
        } else {
            holder = (HistoryItemAdapter.ViewHolder) convertView.getTag();
        }



        //gán giá trị

        HistoryItem historyItem = historyItemList.get(position);
        holder.orderId.setText(String.valueOf(historyItem.getOrderId()));
        holder.tableId.setText(String.valueOf(historyItem.getTableId()));
        holder.priceTotal.setText(String.valueOf(historyItem.getTotalPrice()));
        holder.paymentTime.setText(historyItem.getTimePayment());

        return convertView;
    }
}
