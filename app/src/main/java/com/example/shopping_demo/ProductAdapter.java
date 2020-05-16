package com.example.shopping_demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context mCtx;
    private ArrayList<Product> productList;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    public ProductAdapter(Context mCtx, ArrayList<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        final Product product = productList.get(position);


        holder.productName1.setText(product.getProductName());
        holder.productCompany1.setText(product.getProductCompany());
        //my mystake below here im directly inserting integer value by setText without casting the value
        holder.productPrice1.setText(Integer.toString(product.getProductPrice()));
        holder.shippingCharge1.setText(Integer.toString(product.getShippingCharge()));
        Glide.with(mCtx).load(product.getImage()).into(holder.image1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx,recyclerView_Fragment.class);
                i.putExtra("prodName",product.getProductName());
                i.putExtra("CompName",product.getProductCompany());
                i.putExtra("ProdPrice",product.getProductPrice());
                i.putExtra("ShippCharg",product.getShippingCharge());
                i.putExtra("image",product.getImage());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView productName1;
        TextView productCompany1;
        TextView productPrice1;
        TextView shippingCharge1;
        ImageView image1;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productName1 = itemView.findViewById(R.id.productName);
            productCompany1 = itemView.findViewById(R.id.productCompany);
            productPrice1 = itemView.findViewById(R.id.productPrice);
            shippingCharge1 = itemView.findViewById(R.id.shippingCharge);
            image1 = itemView.findViewById(R.id.imageView);

        }


    }
}
