package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.FoodOrderItem;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class KitchenFoodItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<FoodOrderItem> foodOrderItem;

    public KitchenFoodItemAdapter(Context context, int layout, List<FoodOrderItem> foodOrderItem) {
        this.context = context;
        this.layout = layout;
        this.foodOrderItem = foodOrderItem;
    }

    @Override
    public int getCount() {
        return foodOrderItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView image;
        TextView name, quantityOrdered, tableId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final KitchenFoodItemAdapter.ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new KitchenFoodItemAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewNameKitchenFoodOrdered);
            holder.image = (ImageView) convertView.findViewById(R.id.imageViewKitchenFoodOrdered);
            holder.tableId = (TextView) convertView.findViewById(R.id.tableId);
            holder.quantityOrdered = (TextView)convertView.findViewById(R.id.numberOrdered);
            convertView.setTag(holder);
        } else {
            holder = (KitchenFoodItemAdapter.ViewHolder) convertView.getTag();
        }
        final FoodOrderItem menu = foodOrderItem.get(position); // lay tung cai mot ra
        holder.name.setText(menu.getDishName());
        holder.tableId.setText(String.valueOf(menu.getTableId()));
        holder.quantityOrdered.setText(Integer.toString(menu.getQuantityOrder()));
        String imgUrl = menu.getImage();
        Picasso.with(context).load(imgUrl).into(holder.image);
        return convertView;
    }
}
