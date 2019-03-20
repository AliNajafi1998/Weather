package com.najafi.ali.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.najafi.ali.weather.data.CityDbHelper;
import com.najafi.ali.weather.data.CityModel;

import java.util.ArrayList;
import java.util.List;

public class AddCityFragment extends DialogFragment {


    private RecyclerView recyclerView;
    private SearchView searchView;
    private MyAdapter adapter;
    private AddCityInterface iactivity;
    CityDbHelper dbHelper;
    List<CityModel> citylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_city, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbHelper = new CityDbHelper(getContext());
        searchView = view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                citylist = dbHelper.searchCityByName(query, "20");
                Log.i(AddCityFragment.class.getSimpleName(),
                        "citylist : " + citylist.size() + " items");
                updateDisplay();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        updateDisplay();
        return view;
    }

    private void updateDisplay() {
        if (citylist == null) {
            citylist = new ArrayList<>();
        }
        adapter = new MyAdapter(citylist);
        recyclerView.setAdapter(adapter);

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        private List<CityModel> cityList;

        public MyAdapter(List<CityModel> cityList) {
            this.cityList = cityList;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_layout, viewGroup, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            final CityModel cityModel = cityList.get(position);
            myViewHolder.tv_cityName.setText(cityModel.toString());
            myViewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iactivity.addCity(cityModel.getId());
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return cityList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            Button btn;
            TextView tv_cityName;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                btn = itemView.findViewById(R.id.btn);
                btn.setText("Add");
                btn.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.add_color));
                btn.setBackgroundResource(R.drawable.city_add_btn_bg);
                tv_cityName = itemView.findViewById(R.id.city_name);
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.iactivity = (AddCityInterface) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }


    interface AddCityInterface {
        void addCity(long cityId);
    }

    @Override
    public void onResume() {
        super.onResume();
        changeDialogSize();
    }

    private void changeDialogSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getDialog().getWindow().setLayout((int) (metrics.widthPixels * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT);

    }

}
