package com.example.complaintapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaintapplication.R;
import com.example.complaintapplication.models.HistoryAPI;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CustomViewHolder> {

    private Context context;
    private HistoryAPI[] data;

    public HistoryAdapter(Context context, HistoryAPI[] data){

        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.history, viewGroup, false);

        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {

        HistoryAPI list = data[i];

        String complaintStatus = "";
        if((list.getStatus().toString()).contains("1")){
            complaintStatus = "Pending";
        }
        else{
            complaintStatus = "Resolved";
        }

        customViewHolder.typeOfService.setText(list.getServiceText());
        customViewHolder.createdAt.setText(list.getCreatedAt().toString());
        customViewHolder.address.setText(list.getAddress().toString());
        customViewHolder.city.setText(list.getCityId().toString());
        customViewHolder.status.setText(complaintStatus);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView typeOfService, createdAt, address,city,status;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            typeOfService = itemView.findViewById(R.id.typeOfService);
            createdAt = itemView.findViewById(R.id.createdAt);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            status = itemView.findViewById(R.id.status);
        }
    }

}

