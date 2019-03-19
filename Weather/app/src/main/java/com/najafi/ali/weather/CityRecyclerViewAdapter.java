package com.najafi.ali.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.najafi.ali.weather.data.CityDbHelper;
import com.najafi.ali.weather.data.CityModel;

import java.util.List;

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder> {


    private List<CityModel> citylist;
    private CityDbHelper dbHelper;

    public CityRecyclerViewAdapter( List<CityModel> citylist, CityDbHelper dbHelper) {
        this.citylist = citylist;
        this.dbHelper = dbHelper;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CityModel cityModel = citylist.get(position);
        holder.tv_city_name.setText(cityModel.toString());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.updateCitySelected(cityModel.getId(), false);
                citylist.remove(cityModel);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return citylist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        Button btn;
        TextView tv_city_name;
        public ViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn);
            btn.setText("Delete");
            tv_city_name =  itemView.findViewById(R.id.city_name);
        }
    }

}
