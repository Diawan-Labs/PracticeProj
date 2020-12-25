package com.example.practice.NewRecyclerViewExample;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;

public class MyCustomerRecyclerViewHolder extends RecyclerView.ViewHolder {


    TextView number_name,number_id;

    public MyCustomerRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        number_id=itemView.findViewById(R.id.number_id);
        number_name=itemView.findViewById(R.id.number_name);
        number_id.setOnClickListener(v->
        {
            int val=number_name.getVisibility()==View.GONE? View.VISIBLE:View.GONE;
            number_name.setVisibility(val);
        });
    }
}

