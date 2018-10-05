package com.codingblocks.notes;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onloaddata();
        final EditText noteTitle = findViewById(R.id.etNoteTitle);

        //Adding a view by JAVA
//        LinearLayout linearLayout = findViewById(R.id.linearLayout);
//
//        Button button = new Button(this);
//        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        button.setText("New Button");
//        linearLayout.addView(button);


        //Here the EditText is empty so the String titleNote has the value ""
//        String titleNote = noteTitle.getText().toString();

        ListView listView = findViewById(R.id.listView);
        final NotesAdapter notesAdapter = new NotesAdapter(notes);

        listView.setAdapter(notesAdapter);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnsave=findViewById(R.id.btnsave);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteTitle.getText().toString();
                String time = String.valueOf(System.currentTimeMillis());
                Note note = new Note(title, time);

                notes.add(note);
                notesAdapter.notifyDataSetChanged();

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onsavedata();

            }
        });
    }

    private void onsavedata(){
        SharedPreferences sharedPreferences=getSharedPreferences("note",MODE_PRIVATE);
        Gson gson=new Gson();
        SharedPreferences.Editor ed =sharedPreferences.edit();
        String json=gson.toJson(notes);
        ed.putString("task",json);
        ed.apply();


    }
    private void onloaddata(){

        SharedPreferences sharedPreferences=getSharedPreferences("note",MODE_PRIVATE);
        Gson gson=new Gson();
        String  json=sharedPreferences.getString("task",null);
        Type type=new TypeToken<ArrayList<Note>>() {}.getType();
          notes = gson.fromJson(json,type);
          if(notes==null)
          {
              notes=new ArrayList<>();
          }
    }
}
