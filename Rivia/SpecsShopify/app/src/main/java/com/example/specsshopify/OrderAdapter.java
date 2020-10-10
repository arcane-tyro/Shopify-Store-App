package com.example.specsshopify;

import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    ArrayList<Order> orderArrayList;
    Context context;

    public OrderAdapter(ArrayList<Order> orderArrayList, Context context) {
        this.orderArrayList = orderArrayList;
        this.context = context;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView orderIdTextView, paymentStatusTextView, fulfillmentStatusTextView, refundStatusTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            this.paymentStatusTextView = itemView.findViewById(R.id.paymentStatusTextView);
            this.fulfillmentStatusTextView = itemView.findViewById(R.id.fulfillmentStatusTextView);
            this.refundStatusTextView = itemView.findViewById(R.id.refundStatusTextView);
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_card, parent, false);

        OrderViewHolder orderViewHolder = new OrderViewHolder(view);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        TextView orderIdTextView = holder.orderIdTextView;
        TextView paymentStatusTextView = holder.paymentStatusTextView;
        TextView fulfillmentStatusTextView = holder.fulfillmentStatusTextView;
        TextView refundStatusTextView = holder.refundStatusTextView;

        String orderIDString = "<b>" + "Order ID: " + "</b>" + orderArrayList.get(position).getOrderId();
        orderIdTextView.setText(Html.fromHtml(orderIDString));

        String paymentString = "<b>" + "Payment Status: " + "</b>" + orderArrayList.get(position).getPaymentStatus();
        paymentStatusTextView.setText(Html.fromHtml(paymentString));

        String fulfillmentString = "<b>" + "Fulfillment Status: " + "</b>" + orderArrayList.get(position).getFulfillmentStatus();
        fulfillmentStatusTextView.setText(Html.fromHtml(fulfillmentString));

        String refundString = "<b>" + "Refund Status: " + "</b>" + orderArrayList.get(position).getRefundStatus();
        refundStatusTextView.setText(Html.fromHtml(refundString));
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }
}
