package com.example.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TasksDoneActivity extends AppCompatActivity {
    public static final String DONE = "DONE";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private TextView tasksDonetxt;
    private Button clearbtn;
    ArrayList<Task> tasksDone=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_done);
        tasksDonetxt = findViewById(R.id.tasksDonetxt);
        clearbtn=findViewById(R.id.clearbtn);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        giveTasksDone();
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove(DONE);
                editor.commit();
                tasksDonetxt.setText("");
            }
        });
    }

    private void giveTasksDone() {
        Gson gson = new Gson();
        String json = prefs.getString(DONE, "");
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            tasksDone = gson.fromJson(json, type);
            for(int i=0;i<tasksDone.size();i++){
                tasksDonetxt.setText(tasksDonetxt.getText()+tasksDone.get(i).toString()+"\n");
            }
        }
    }
}