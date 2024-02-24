package com.devdroid.assignmentapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.MyViewHolder>{
   private Context context;
    private List<CartModel> productModelList;
    private List<ProductModel> productModelList1;

    public CartAdapter(Context context) {
        this.context = context;
        productModelList = new ArrayList<>();
    }
    public void addProduct(CartModel productModel){
        productModelList.add(productModel);
        notifyDataSetChanged();
    }
    public List<CartModel> getSelectedItems(){
        List<CartModel> cartitems = new ArrayList<>();
        for(int i=0;i<productModelList.size();i++){
            if(productModelList.get(i).is_selected){
                cartitems.add(productModelList.get(i));
            }
        }
        return cartitems;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {// To show the list of products in the recycler view
        CartModel productModel =productModelList.get(position);
        holder.title.setText(productModel.getProductName());
        holder.price.setText(productModel.getProductPrice());
        holder.qty.setText(productModel.getProductQty());
        holder.description.setText(productModel.getDescription());


        Glide.with(context).load(productModel.getProductImage())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productModel.is_selected){
                    holder.main.setBackgroundColor(context.getResources().getColor(R.color.white));
                    productModel.is_selected=false;
                }else{
                    holder.main.setBackgroundColor(context.getResources().getColor(R.color.light_blue));

                    productModel.is_selected=true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title,qty,price,description;
        private ImageView img;
        private LinearLayout main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            qty=itemView.findViewById(R.id.qty);
         description=itemView.findViewById(R.id.description);
            price=itemView.findViewById(R.id.price);
            img=itemView.findViewById(R.id.image);
            main =itemView.findViewById(R.id.mainLayout);


        }
    }
}
