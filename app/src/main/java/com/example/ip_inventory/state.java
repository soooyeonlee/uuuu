package com.example.ip_inventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class state extends Activity {
    String myJSON;
    private static final String TAG_RESULT = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_AMOUNT = "amount";
    private TableLayout frozenTable;

    JSONArray products = null;
    ArrayList<HashMap<String, String>> productList;
    ScrollView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_item);
        //grid = (GridView) findViewById(R.id.gridView);
        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        frozenTable = (TableLayout)findViewById(R.id.frozenTable);
        productList = new ArrayList<HashMap<String,String>>();
        getData("http://54.180.116.239/PHP_connection.php");
    }



    protected void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            products = jsonObj.getJSONArray(TAG_RESULT);
            TableRow frozenRow = new TableRow(this);
            TextView frozenCell_id = new TextView(this);
            TextView frozenCell_name = new TextView(this);
            TextView frozenCell_price = new TextView(this);
            TextView frozenCell_amount = new TextView(this);

            frozenCell_id.setWidth(250);
            frozenCell_name.setWidth(250);
            frozenCell_price.setWidth(250);
            frozenCell_amount.setWidth(250);
            frozenCell_id.setText("id");
            frozenRow.addView(frozenCell_id);
            frozenCell_name.setText("이름");
            frozenRow.addView(frozenCell_name);
            frozenCell_price.setText("가격");
            frozenRow.addView(frozenCell_price);
            frozenCell_amount.setText("재고");
            frozenRow.addView(frozenCell_amount);
            frozenTable.addView(frozenRow);

            for(int i =0; i < products.length(); i++){
                JSONObject c = products.getJSONObject(i);
                frozenRow = new TableRow(this);
                frozenCell_id = new TextView(this);
                frozenCell_name = new TextView(this);
                frozenCell_price = new TextView(this);
                frozenCell_amount = new TextView(this);

                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String price = c.getString(TAG_PRICE);
                String amount = c.getString(TAG_AMOUNT);

                HashMap<String, String> items = new HashMap<String, String>();

                items.put(TAG_ID,id);
                items.put(TAG_NAME,name);
                items.put(TAG_PRICE,price);
                items.put(TAG_AMOUNT,amount);

                productList.add(items);

                frozenCell_id.setText(id);
                frozenRow.addView(frozenCell_id);
                frozenCell_name.setText(name);
                frozenRow.addView(frozenCell_name);
                frozenCell_price.setText(price);
                frozenRow.addView(frozenCell_price);
                frozenCell_amount.setText(amount);
                frozenRow.addView(frozenCell_amount);
                frozenTable.addView(frozenRow);
            }

            //ListAdapter adapter = new SimpleAdapter(
            //        state.this, productList, R.layout.grid_item,
            //       new String[]{TAG_ID,TAG_NAME,TAG_PRICE,TAG_AMOUNT},
            //        new int[]{ R.id.id, R.id.name, R.id.price, R.id.amount}
            // );
            //grid.setAdapter(adapter);.
            //grid.addView(frozenTable);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}
