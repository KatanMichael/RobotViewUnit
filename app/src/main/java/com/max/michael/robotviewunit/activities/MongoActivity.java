package com.max.michael.robotviewunit.activities;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import com.max.michael.robotviewunit.R;

public class MongoActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.mongo_screen);
    }
}
