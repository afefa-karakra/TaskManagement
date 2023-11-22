package com.example.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton tasksListBtn;
    private ImageButton taskDoneImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasksListBtn =findViewById(R.id.tasksListBtn);
        taskDoneImg =findViewById(R.id.taskDoneImg);
    }
    public void onClickTasksList(View view){
        Intent intent=new Intent(this,TaskListActivity.class);
        startActivity(intent);
    }
    public void onClickTasksDone(View view){
        Intent intent=new Intent(this,TasksDoneActivity.class);
        startActivity(intent);
    }
}