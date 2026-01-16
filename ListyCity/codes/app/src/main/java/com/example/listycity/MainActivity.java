package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addCityBtn, deleteCityBtn;
    String selectedCity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton","Vancouver", "Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","Paris"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, R.id.content_view, dataList);
        cityList.setAdapter(cityAdapter);

        addCityBtn = findViewById(R.id.btn_add_city);
        addCityBtn.setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setHint("Enter city name");

            new AlertDialog.Builder(this)
                    .setTitle("Add City")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        String city = input.getText().toString().trim();

                        if (TextUtils.isEmpty(city)) {
                            Toast.makeText(this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dataList.add(0, city);
                        cityAdapter.notifyDataSetChanged();
                        cityList.smoothScrollToPosition(0);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });


        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = dataList.get(position);
            Toast.makeText(this, "Selected: " + selectedCity, Toast.LENGTH_SHORT).show();
        });

        deleteCityBtn = findViewById(R.id.btn_delete_city);
        deleteCityBtn.setOnClickListener(v -> {
            if (selectedCity == null) {
                Toast.makeText(this, "Tap a city first", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedCity);
            cityAdapter.notifyDataSetChanged();
            selectedCity = null;
        });

    }
}