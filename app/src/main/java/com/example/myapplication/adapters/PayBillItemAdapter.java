package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.PayBillItem;

import java.text.DecimalFormat;
import java.util.List;

public class PayBillItemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<PayBillItem> payBillItemList;

    private class ViewHolder{
        TextView STT, dishName, quantityOrder, priceEach, priceTotal;

    }
    public PayBillItemAdapter(Context context, int layout, List<PayBillItem> payBillItemList) {
        this.context = context;
        this.layout = layout;
        this.payBillItemList = payBillItemList;
    }
    @Override
    public int getCount() {
        return payBillItemList.size();
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
        PayBillItemAdapter.ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new PayBillItemAdapter.ViewHolder();

            holder.STT = (TextView) convertView.findViewById(R.id.bill_item_stt);
            holder.dishName= (TextView)convertView.findViewById(R.id.bill_item_dish_name);
            holder.quantityOrder = (TextView)convertView.findViewById(R.id.bill_item_sl);
            holder.priceEach = (TextView)convertView.findViewById(R.id.bill_item_price_each);
            holder.priceTotal = (TextView)convertView.findViewById(R.id.bill_item_price_total);
            convertView.setTag(holder);
        } else {
            holder = (PayBillItemAdapter.ViewHolder) convertView.getTag();
        }
        PayBillItem payBillItem = payBillItemList.get(position);

        holder.STT.setText(String.valueOf(payBillItem.getSTT()));
        holder.dishName.setText(payBillItem.getDishName());
        holder.quantityOrder.setText(String.valueOf(payBillItem.getQuantityOrder()));
        DecimalFormat df= new DecimalFormat("###,###,###");
        //  int i = Integer.valueOf(payBillItem.getPriceEach());
        String priceEachString = df.format(payBillItem.getPrice());
        holder.priceEach.setText(priceEachString);
        String priceTotalString = df.format(payBillItem.getPriceTotal());
        holder.priceTotal.setText(priceTotalString);
        return convertView;
    }
}
