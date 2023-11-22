package com.example.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class TaskListActivity extends AppCompatActivity {

    public static final String TITLE = "TITLE";
    public static final String DONE = "DONE";
    private EditText addTaskText;
    private ImageButton addTaskbtn;
    private Button donebtn;
    ListView listview1;
    ArrayList<Task> tasksList=new ArrayList<>();
    ArrayList<Task> tasksDone=new ArrayList<>();
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        setupViews();
        setupSharedPrefs();
        giveTasks();
        addTaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(addTaskText.getText().toString());
                addTaskText.setText("");
            }
        });

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedDoneTasks();
            }
        });
    }

    private void setupViews() {
        addTaskText = findViewById(R.id.addTaskText);
        addTaskbtn = findViewById(R.id.addTaskbtn);
        donebtn=findViewById(R.id.donebtn);
        listview1 = findViewById(R.id.listview1);
    }
    private void setupSharedPrefs(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    private void giveTasks(){
        Gson gson = new Gson();
        String json = prefs.getString(TITLE, "");
        if (!json.equals("")) {
            listview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            tasksList = gson.fromJson(json, type);
            ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, tasksList);
            listview1.setAdapter(adapter);
        }
    }
    private void saveTask(String title) {
        if(!title.equals("")){
            Gson gson=new Gson();
            tasksList.add(new Task(title));
            String json=gson.toJson(tasksList);
            editor.putString(TITLE,json);
            editor.commit();
            giveTasks();
        }}

    private void checkedDoneTasks() {
        for (int i=tasksList.size()-1;i>=0;i--) {
            if (listview1.isItemChecked(i)) {
                Gson gson = new Gson();
                String json = prefs.getString(DONE, "");
                if (!json.equals("")) {
                    Type type = new TypeToken<ArrayList<Task>>(){}.getType();
                    tasksDone = gson.fromJson(json, type);
                    Gson gson2=new Gson();
                    tasksDone.add(tasksList.get(i));
                    String json2=gson2.toJson(tasksDone);
                    editor.putString(DONE,json2);
                    editor.commit();
                }else{
                    Gson gson2=new Gson();
                    tasksDone.add(tasksList.get(i));
                    String json2=gson2.toJson(tasksDone);
                    editor.putString(DONE,json2);
                    editor.commit();
                }
                tasksList.remove(i);
            }
        }
        Gson gson = new Gson();
        String json=gson.toJson(tasksList);
        editor.putString(TITLE, json);
        editor.commit();
        giveTasks();
    }
}