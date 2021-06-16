package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Table;

import java.util.List;

public class TableItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Table> tableList;


    /// tạo ra view holder để ko phải ánh xạ lại những items đã lướt qua khi mình lướt lại
    private class ViewHolder{
        ImageView image;
        TextView name;
    }

    public TableItemAdapter(Context context, int layout, List<Table> tableList) {
        this.context = context;
        this.layout = layout;
        this.tableList = tableList;
    }

    @Override
    public int getCount() {
        // very important
        return tableList.size();
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
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            //ánh xạ view
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.table_name);
            holder.image = (ImageView) convertView.findViewById(R.id.table_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //gán giá trị

        Table table = tableList.get(position);
        holder.name.setText("Bàn số "+ table.getTableId());
        // holder.chair_number.setText("Số ghế "+tableItem.getChair_number());
        holder.image.setImageResource(table.getImage());
        holder.image.setBackgroundColor(Color.parseColor(table.getColor()));

        return convertView;
    }
}