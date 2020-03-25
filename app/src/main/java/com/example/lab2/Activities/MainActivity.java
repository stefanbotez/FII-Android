package com.example.lab2.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab2.Adapters.CustomAdapter;
import com.example.lab2.Database.DatabaseHelper;
import com.example.lab2.R;
import com.example.lab2.Models.RowModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TITLE_EXTRA = "com.example.lab2.TITLE";
    public static final String DESCRIPTION_EXTRA = "com.example.lab2.DESCRIPTION";
    static final String TEXTVIEWKEY = "TEXTVIEWKEY";


    DatabaseHelper db;
    ListView listView;
    TextView textView;
    int lastClicked = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra(TITLE_EXTRA);
        String itemDescription = intent.getStringExtra(DESCRIPTION_EXTRA);
        db = new DatabaseHelper(this);

        listView = (ListView)findViewById(R.id.listView);
        textView = (TextView)findViewById(R.id.textView);

        final ArrayList<RowModel> myList = new ArrayList<>();
        Cursor data = db.getData();

        if(itemTitle != null && itemDescription != null) {
            db.addData(itemTitle, itemDescription, R.drawable.not_found);
        }
        while(data.moveToNext()){
            myList.add(new RowModel(data.getString(1), data.getString(2), data.getInt(3)));
        }


        listView.setAdapter(new CustomAdapter(this, myList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "CLICKED ON " + position + " element", Toast.LENGTH_SHORT).show();
                lastClicked = position;
                textView.setText(String.format("CLICKED %d", lastClicked));

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myList.get(position).getDescription()));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //Toast.makeText(getApplicationContext(), "onResoreInstanceState", Toast.LENGTH_SHORT).show();
        lastClicked = savedInstanceState.getInt(TEXTVIEWKEY);
        textView.setText(String.format("LOADED %s", lastClicked));
        
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TEXTVIEWKEY, lastClicked);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AddItem:
                AddItem();
                return true;
            case R.id.Sensors:
                startActivity(new Intent(this, SensorsActivity.class));
                return true;
            case R.id.Settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.Close:
                Close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void AddItem(){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    private void Close(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to close?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
