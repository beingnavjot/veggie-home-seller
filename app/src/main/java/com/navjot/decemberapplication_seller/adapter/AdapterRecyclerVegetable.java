package com.navjot.decemberapplication_seller.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.navjot.decemberapplication_seller.R;
import com.navjot.decemberapplication_seller.model.Vegetable;

import java.util.ArrayList;

public class AdapterRecyclerVegetable extends RecyclerView.Adapter<AdapterRecyclerVegetable.Holder> {


    LayoutInflater inflater;
    Context context;
    ArrayList<Vegetable> VegetablesArrayList;


    public AdapterRecyclerVegetable(Context context, ArrayList<Vegetable> VegetablesArrayList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        //  this.images=images;
        this.VegetablesArrayList = VegetablesArrayList;
    }

    @NonNull
    @Override
    public AdapterRecyclerVegetable.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_item, parent, false);
        Holder h = new Holder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerVegetable.Holder holder, int position) {

        Vegetable ob=VegetablesArrayList.get(position);
        holder.textViewName.setText(ob.getName());
        holder.textViewPrice.setText( ob.getPrice()+"");

    }

    @Override
    public int getItemCount() {
        //   return 0;
        return VegetablesArrayList.size();
    }




    class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName,textViewPrice;



        // Context context;

        public Holder(@NonNull final View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.custom_itemImage);
            textViewName=itemView.findViewById(R.id.vegetableName);
            textViewPrice=itemView.findViewById(R.id.vegetablePrice);



        }
    }


}




