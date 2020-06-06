package com.example.caloriecount;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS ="sharedPrefs";
    public static final String CALORIE= "text";
    private String text;

    EditText etcalorie;
    Button btchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etcalorie=findViewById(R.id.caloriecount);
        btchange=findViewById(R.id.change);

        loadData();
        updateViews();

        btchange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(btchange.getText().toString().equals("Edit")){
                    btchange.setText("Save");
                    btchange.setTextColor(Color.GREEN);
                    etcalorie.setCursorVisible(true);
                    etcalorie.setEnabled(true);
                    etcalorie.setTextColor(Color.WHITE);
                }
                else {
                    btchange.setText("Edit");
                    btchange.setTextColor(Color.RED);
                    etcalorie.setCursorVisible(false);
                    etcalorie.setEnabled(false);
                    etcalorie.setTextColor(Color.GRAY);
                    saveData();
                }
            }
        });
    }

    public void saveData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CALORIE, etcalorie.getText().toString());
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        text=sharedPreferences.getString(CALORIE,"0");
    }
    public void updateViews(){
        etcalorie.setText(text);

    }
}


