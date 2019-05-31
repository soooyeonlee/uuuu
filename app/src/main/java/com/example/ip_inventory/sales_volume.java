package com.example.ip_inventory;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ip_inventory.Constant;
import com.example.ip_inventory.PostRequestHandler;
import com.example.ip_inventory.ViewActivity;

import java.util.HashMap;

public class sales_volume extends AppCompatActivity {

    private EditText mName, mDesignation, mSalary;
    private Button mBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_volume);

        // Initialize EditText View
        mName = (EditText) findViewById(R.id.name);
        mDesignation = (EditText) findViewById(R.id.designation);
        mSalary = (EditText) findViewById(R.id.salary);

        mBtnAdd = (Button) findViewById(R.id.btn_add);
    }

    // Create
    public void createEmployee(View view){

        String name = mName.getText().toString();
        String designation = mDesignation.getText().toString();
        String salary = mSalary.getText().toString();

        HashMap<String, String> requestedParams = new HashMap<>();
        requestedParams.put("name", name);
        requestedParams.put("designation", designation);
        requestedParams.put("salary", salary);
        Log.d("HashMap", requestedParams.get("name"));
        Toast.makeText(getApplicationContext(), "Success!!! Employee Added Name: " + requestedParams.get("name"), Toast.LENGTH_LONG).show();

        PostRequestHandler postRequestHandler = new PostRequestHandler(Constant.CREATE_URL, requestedParams);
        postRequestHandler.execute();

        employeeList(view);
    }

    public void employeeList(View view) {
        Intent intent = new Intent(sales_volume.this, ViewActivity.class);
        startActivity(intent);
    }

}


