package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.CountByDishNameItem;
import java.util.List;

public class CountByDishNameItemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<CountByDishNameItem> itemList;

    private class ViewHolder{
        TextView STT, dishName, sumQuantity;
    }

    public CountByDishNameItemAdapter(Context context, int layout, List<CountByDishNameItem> itemList) {
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
        CountByDishNameItemAdapter.ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new CountByDishNameItemAdapter.ViewHolder();
            holder.STT = (TextView) convertView.findViewById(R.id.cbd_item_stt);
            holder.dishName= (TextView)convertView.findViewById(R.id.cbd_item_dish_name);
            holder.sumQuantity = (TextView)convertView.findViewById(R.id.cbd_item_sl);
            convertView.setTag(holder);
        } else {
            holder = (CountByDishNameItemAdapter.ViewHolder) convertView.getTag();
        }
        CountByDishNameItem item = itemList.get(position);
        holder.STT.setText(String.valueOf(item.getSTT()));
        holder.dishName.setText(item.getDishName());
        holder.sumQuantity.setText(String.valueOf(item.getSumQuantity()));
        return convertView;
    }
}
