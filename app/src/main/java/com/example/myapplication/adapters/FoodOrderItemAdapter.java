package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class FoodOrderItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<FoodOrderItem> foodOrderItem;

    public FoodOrderItemAdapter(Context context, int layout, List<FoodOrderItem> foodOrderItem) {
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
        TextView name;
        TextView price;
        TextView number;
        ImageButton plusBtn, minusBtn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewNameFoodOrderItem);
            holder.price = (TextView) convertView.findViewById(R.id.textViewPriceFoodOrderItem);
            holder.image = (ImageView) convertView.findViewById(R.id.imageViewFoodOrderItem);
            holder.plusBtn= (ImageButton)convertView.findViewById(R.id.plus_btn);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.minusBtn = (ImageButton) convertView.findViewById(R.id.minus_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FoodOrderItem menu = foodOrderItem.get(position); // lay tung cai mot ra
        holder.name.setText(menu.getDish_name());
        DecimalFormat df= new DecimalFormat("###,###,###");
        String priceString = df.format(menu.getPrice());
        holder.price.setText(priceString+"đ");
        holder.number.setText(Integer.toString(menu.getNumber()));
        holder.plusBtn.setImageResource(R.drawable.plus);
        holder.minusBtn.setImageResource(R.drawable.minus);

        String imgUrl = menu.getImageUrl();
        Picasso.with(context).load(imgUrl).into(holder.image);


        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu.getNumber() > 1){
                    menu.setNumber(menu.getNumber()-1);

                }
                holder.number.setText(Integer.toString(menu.getNumber()));

            }
        });

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menu.setNumber(menu.getNumber()+1);


                holder.number.setText(Integer.toString(menu.getNumber()));

            }
        });

        return convertView;
    }
}