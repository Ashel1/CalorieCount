package com.example.caloriecount;

import androidx.annotation.NonNull;
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
import android.widget.ListView;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS ="sharedPrefs";
    public static final String CALORIE= "text";
    public static final String NAME ="Name" ;
    public static final String AGE="Age";
    private String textc,textn,texta;


    EditText etcalorie,etname,etage;
    Button btchange,btload;
    DatabaseReference databaseUsers;
    ListView listViewUser;
    List<UserData> userList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseUsers= FirebaseDatabase.getInstance().getReference("User");
        etcalorie=findViewById(R.id.caloriecount);
        etname=findViewById(R.id.name);
        etage=findViewById(R.id.age);
        btchange=findViewById(R.id.change);
        btload=findViewById(R.id.load);
        listViewUser=findViewById(R.id.listViewUser);
        userList= new ArrayList<>();

        loadData();
        updateViews();

        btchange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String id = databaseUsers.push().getKey();
                if(btchange.getText().toString().equals("Edit")){
                    changeToSave();
                }
                else {
                        UserData user= new UserData(etname.getText().toString(),etage.getText().toString(),etcalorie.getText().toString());
                    databaseUsers.child(id).setValue(user);
                    changeToEdit();
                    saveData();
                }
            }
        });

        btload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userList.clear();
                        for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                            UserData user= userSnapshot.getValue(UserData.class);
                            userList.add(user);
                        }

                        UserList adapter= new UserList(MainActivity.this, userList);
                        listViewUser.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }



    public void changeToSave(){
        btchange.setText("Save");
        btchange.setTextColor(Color.GREEN);
        etcalorie.setCursorVisible(true);
        etcalorie.setEnabled(true);
        etcalorie.setTextColor(Color.WHITE);
        etname.setCursorVisible(true);
        etname.setEnabled(true);
        etname.setTextColor(Color.WHITE);
        etage.setCursorVisible(true);
        etage.setEnabled(true);
        etage.setTextColor(Color.WHITE);
    }

    public void changeToEdit(){
        btchange.setText("Edit");
        btchange.setTextColor(Color.RED);
        etcalorie.setCursorVisible(false);
        etcalorie.setEnabled(false);
        etcalorie.setTextColor(Color.GRAY);
        etname.setCursorVisible(false);
        etname.setEnabled(false);
        etname.setTextColor(Color.GRAY);
        etage.setCursorVisible(false);
        etage.setEnabled(false);
        etage.setTextColor(Color.GRAY);
    }

    public void saveData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CALORIE, etcalorie.getText().toString());
        editor.putString(NAME, etname.getText().toString());
        editor.putString(AGE, etage.getText().toString());

        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        textc=sharedPreferences.getString(CALORIE,"");
        textn=sharedPreferences.getString(NAME,"");
        texta=sharedPreferences.getString(AGE,"");
    }
    public void updateViews(){
        etcalorie.setText(textc);
        etname.setText(textn);
        etage.setText(texta);

    }
}


