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

public class InventoryActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<Product>();

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    public void setAdapter(){
        recyclerView = findViewById(R.id.inventory_recycler_view);
        layoutManager = new LinearLayoutManager(InventoryActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        ProductAdapter productAdapter  = new ProductAdapter(products, InventoryActivity.this);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        InventoryActivity.DownloadTask task = new InventoryActivity.DownloadTask();
        task.execute("https://specs-shop.myshopify.com/admin/api/2020-10/products.json");

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
                String productInfo = jsonObject.getString("products");
                Log.i("JSON", productInfo);
                JSONArray arr = new JSONArray(productInfo);
                for(int i = 0; i < arr.length(); i++){
                    Product product = new Product();
                    JSONObject jsonPart = arr.getJSONObject(i);
//                    Log.i("Product ID", jsonPart.getString("id"));
                    product.setProductId(jsonPart.getString("id"));
                    product.setProductName(jsonPart.getString("title"));

                    String variants = jsonPart.getString("variants");
                    JSONArray variantArray = new JSONArray(variants);
                    JSONObject variantJSON = variantArray.getJSONObject(0);
                    product.setProductQuantity(variantJSON.getString("inventory_quantity"));

                    products.add(product);
               }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(products.size() > 0){
                for(int i = 0; i < products.size(); i++){
                    Log.i("Product", Integer.toString(i + 1));
                    Log.i("Product ID", products.get(i).getProductId());
                    Log.i("Product Name", products.get(i).getProductName());
                    Log.i("Quantity", products.get(i).getProductQuantity());
                }
                setAdapter();
            }

        }
    }

}