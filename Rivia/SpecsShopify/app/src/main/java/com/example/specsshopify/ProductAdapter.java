package com.example.specsshopify;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ArrayList<Product> productArrayList;
    Context context;

    public ProductAdapter(ArrayList<Product> productArayList, Context context) {
        this.productArrayList = productArayList;
        this.context = context;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView productIdTextView, productNameTextView, productQuantityTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.productIdTextView = itemView.findViewById(R.id.productIdTextView);
            this.productNameTextView = itemView.findViewById(R.id.productNameTextView);
            this.productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_card, parent, false);

        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        TextView productIdTextView = holder.productIdTextView;
        TextView productNameTextView = holder.productNameTextView;
        TextView productQuantityTextView = holder.productQuantityTextView;

        String productIDString = "<b>" + "Product ID: " + "</b>" + productArrayList.get(position).getProductId();
        productIdTextView.setText(Html.fromHtml(productIDString));

        String nameString = "<b>" + "Product Name: " + "</b>" + productArrayList.get(position).getProductName();
        productNameTextView.setText(Html.fromHtml(nameString));

        String quantityString = "<b>" + "Quantity : " + "</b>" + productArrayList.get(position).getProductQuantity();
        productQuantityTextView.setText(Html.fromHtml(quantityString));
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}