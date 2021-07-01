
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

public class FoodOrderBookedItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<FoodOrderItem> foodOrderItem;

    public FoodOrderBookedItemAdapter(Context context, int layout, List<FoodOrderItem> foodOrderItem) {
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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewNameFoodOrderBookedItem);
            holder.price = (TextView) convertView.findViewById(R.id.textViewPriceFoodOrderBookedItem);
            holder.image = (ImageView) convertView.findViewById(R.id.imageViewFoodOrderBookedItem);

            holder.number = (TextView) convertView.findViewById(R.id.numberBooked);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FoodOrderItem menu = foodOrderItem.get(position); // lay tung cai mot ra
        holder.name.setText(menu.getDishName());
        DecimalFormat df= new DecimalFormat("###,###,###");
        String priceString = df.format(menu.getPrice());
        holder.price.setText(priceString+"đ");
        holder.number.setText(Integer.toString(menu.getQuantityOrder()));


        String imgUrl = menu.getImage();
        Picasso.with(context).load(imgUrl).into(holder.image);






        return convertView;
    }
}