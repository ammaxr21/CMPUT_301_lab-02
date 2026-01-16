package com.example.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addButton;
    Button deleteButton;
    String selectedCity = null;
    int selectedIndex =  -1;

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
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        String []cities = {"Edmonton","Vancouver","Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = dataList.get(position);
            selectedIndex = position;
            Toast.makeText(MainActivity.this, selectedCity + " selected. Press delete to remove.", Toast.LENGTH_SHORT).show();
        });

        addButton.setOnClickListener(v-> AddCityPrompt());

        deleteButton.setOnClickListener( v -> {
            if (selectedIndex > -1 && selectedCity != null) {
                dataList.remove(selectedIndex);
                cityAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, selectedCity + " has been deleted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Please select a city first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddCityPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Enter City");
        builder.setMessage("Enter City");

        EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            String inputCity = input.getText().toString().trim();
            if (!inputCity.isEmpty()) {
                dataList.add(inputCity);
                cityAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, inputCity+" has been added to the list.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}