package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {
    EditText itemTitleText;
    EditText itemDescriptionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        itemTitleText = (EditText) findViewById(R.id.ItemTitle);
        itemDescriptionText = (EditText) findViewById(R.id.ItemDescription);
        final Button button = findViewById(R.id.AddItemButton);
    }

    void AddItem(View v){
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString(MainActivity.TITLE_EXTRA, itemTitleText.getText().toString());
        intent.putExtra(MainActivity.DESCRIPTION_EXTRA, itemDescriptionText.getText().toString());
        intent.putExtras(extras);
        Toast.makeText(getApplicationContext(), "SENT " + itemTitleText.getText() + " " + itemDescriptionText.getText(), Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }
}
