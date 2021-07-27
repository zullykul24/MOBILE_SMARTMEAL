package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.CountByDishNameItem;
import com.example.myapplication.models.CountByUserItem;

import java.util.List;

public class CountByUserItemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<CountByUserItem> itemList;

    private class ViewHolder{
        TextView STT, fullName, sumQuantity;
    }

    public CountByUserItemAdapter(Context context, int layout, List<CountByUserItem> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
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
        CountByUserItemAdapter.ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new CountByUserItemAdapter.ViewHolder();
            holder.STT = (TextView) convertView.findViewById(R.id.cbu_item_stt);
            holder.fullName= (TextView)convertView.findViewById(R.id.cbu_item_full_name);
            holder.sumQuantity = (TextView)convertView.findViewById(R.id.cbu_item_sl);
            convertView.setTag(holder);
        } else {
            holder = (CountByUserItemAdapter.ViewHolder) convertView.getTag();
        }
        CountByUserItem item = itemList.get(position);
        holder.STT.setText(String.valueOf(item.getSTT()));
        holder.fullName.setText(item.getFullname());
        holder.sumQuantity.setText(String.valueOf(item.getTotaldishes()));
        return convertView;
    }
}
