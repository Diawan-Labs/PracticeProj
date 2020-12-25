package com.example.practice.NewRecyclerViewExample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;
import com.example.practice.NewRecyclerViewExample.model.Number;
import com.example.practice.databinding.RecyclerViewItemsLeftBinding;
import com.example.practice.databinding.RecyclerViewItemsRightBinding;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyCustomRecyclerViewAdapter extends RecyclerView.Adapter<MyCustomerRecyclerViewHolder> implements Filterable {

    public  static  final int MSG_TYPE_LEFT=0;
    public  static  final int MSG_TYPE_RIGHT=1;
    private static final String TAG = "MyCustomRecyclerViewAdapter";
    List<Number> numberList;
    List<Number> numberListAll;
    MyCustomerRecyclerViewHolder customerRecyclerViewHolder;
    Context context;
    RecyclerViewItemsRightBinding recyclerViewItemsRightBinding;
    RecyclerViewItemsLeftBinding RecyclerViewItemsLeftBinding;
    public MyCustomRecyclerViewAdapter(List<Number> numberList, Context context) {
        this.numberList = numberList;
        numberListAll=new ArrayList<>(numberList);
        this.context = context;
    }

    @NonNull
    @Override
    public MyCustomerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if(viewType==MSG_TYPE_RIGHT) {

            recyclerViewItemsRightBinding = recyclerViewItemsRightBinding.inflate(layoutInflater, parent, false);
            customerRecyclerViewHolder = new MyCustomerRecyclerViewHolder(recyclerViewItemsRightBinding.getRoot());
        }
        else  if(viewType==MSG_TYPE_LEFT)
        {
            RecyclerViewItemsLeftBinding = RecyclerViewItemsLeftBinding.inflate(layoutInflater, parent, false);
            customerRecyclerViewHolder = new MyCustomerRecyclerViewHolder(RecyclerViewItemsLeftBinding.getRoot());
        }
        return customerRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCustomerRecyclerViewHolder holder, int position) {
        Number number=numberList.get(position);
        holder.number_id.setText(String.valueOf(number.getId()));
        holder.number_name.setText(number.getName());
    }
    @Override
    public int getItemCount() {
        return numberList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d(TAG,"inside performFiltering::"+constraint);
                List<Number> filteredList = new ArrayList<>();
               if(constraint.toString().isEmpty())
               {
                   filteredList.addAll(numberListAll);
               }
               else
              {
                   for(Number num:numberListAll)
                   {
                       if(num.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                           System.out.println("num::" + num);
                           filteredList.add(num);
                       }
                   }
               }
                System.out.println("filteredList.size::"+filteredList.size());
                FilterResults filterResults=new FilterResults();
                filterResults.values=filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                numberList.clear();
                numberList.addAll((Collection<? extends Number>) results.values);
                System.out.println("numberList.size::"+numberList.size());
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0)
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;
    }
}
