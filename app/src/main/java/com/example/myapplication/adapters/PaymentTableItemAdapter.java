package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.PaymentTableItem;

import java.util.List;

public class PaymentTableItemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<PaymentTableItem> paymentTableItemList;

    private class ViewHolder{
        TextView name;

    }
    public PaymentTableItemAdapter(Context context, int layout, List<PaymentTableItem> paymentTableItemList) {
        this.context = context;
        this.layout = layout;
        this.paymentTableItemList = paymentTableItemList;
    }
    @Override
    public int getCount() {
        return paymentTableItemList.size();
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
        PaymentTableItemAdapter.ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new PaymentTableItemAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.text_payment_table);
            convertView.setTag(holder);
        } else {
            holder = (PaymentTableItemAdapter.ViewHolder) convertView.getTag();
        }



        //gán giá trị

        PaymentTableItem paymentTableItem = paymentTableItemList.get(position);
        holder.name.setText("Bàn " + paymentTableItem.getTableName());

        return convertView;
    }
}