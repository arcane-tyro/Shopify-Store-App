package com.example.specsshopify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OrdersListActivity extends AppCompatActivity {

    ArrayList<Order> orders = new ArrayList<Order>();

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    public void setAdapter(){
        recyclerView = findViewById(R.id.order_recycler_view);
        layoutManager = new LinearLayoutManager(OrdersListActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        OrderAdapter orderAdapter = new OrderAdapter(orders, OrdersListActivity.this);
        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        OrdersListActivity.DownloadTask task = new OrdersListActivity.DownloadTask();
        task.execute("https://specs-shop.myshopify.com/admin/api/2020-10/orders.json?status=any");

    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        final String basicAuth = "Basic " + Base64.encodeToString("411a5dbf05e33a301fe73e4fc825d0ff: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".getBytes(), Base64.NO_WRAP);

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty ("Authorization", basicAuth);
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }
                return  result;
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.i("JSON", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String orderInfo = jsonObject.getString("orders");
                //Log.i("JSON", orderInfo);
                JSONArray arr = new JSONArray(orderInfo);
                for(int i = 0; i < arr.length(); i++){
                    Order order = new Order();
                    JSONObject jsonPart = arr.getJSONObject(i);
                    order.setOrderId(jsonPart.getString("id"));

                    String paymentStatus = (jsonPart.getString("financial_status")).toUpperCase();
                    order.setPaymentStatus(paymentStatus);

                    if(paymentStatus.equals("REFUNDED"))
                        order.setRefundStatus("SUCCESS");

                    order.setFulfillmentStatus(jsonPart.getString("fulfillment_status"));
                    orders.add(order);
//                    Log.i("Order ID", jsonPart.getString("id"));
//                    Log.i("Order Number", jsonPart.getString("order_number"));
//                    Log.i("Customer ID", jsonPart.getString("user_id"));
//                    Log.i("payment_status", jsonPart.getString("financial_status"));
//                    Log.i("fulfillment_status", jsonPart.getString("fulfillment_status"));
//                    Log.i("Order Price", jsonPart.getString("total_line_items_price"));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(orders.size() > 0){
                for(int i = 0; i < orders.size(); i++){
                    Log.i("Order", Integer.toString(i + 1));
                    Log.i("Order ID", orders.get(i).getOrderId());
                    Log.i("payment_status", orders.get(i).getPaymentStatus());
                    Log.i("fulfillment_status", orders.get(i).getFulfillmentStatus());
                }
                setAdapter();
            }

        }
    }

}