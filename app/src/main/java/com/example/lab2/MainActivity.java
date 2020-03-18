package com.example.lab2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TITLE_EXTRA = "com.example.lab2.TITLE";
    public static final String DESCRIPTION_EXTRA = "com.example.lab2.DESCRIPTION";
    final String TEXTVIEWKEY = "TEXTVIEWKEY";
    ListView listView;
    TextView textView;
    int lastClicked = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra(TITLE_EXTRA);
        String itemDescription = intent.getStringExtra(DESCRIPTION_EXTRA);

        listView = (ListView)findViewById(R.id.listView);
        textView = (TextView)findViewById(R.id.textView);

        final ArrayList<RowModel> myList = new ArrayList<>();
        myList.add(new RowModel("Chrome" , "https://www.google.com/chrome", R.drawable.chrome));
        myList.add(new RowModel("Safari" , "https://www.apple.com/safari/", R.drawable.safari));
        myList.add(new RowModel("Firefox" , "https://www.mozilla.org/en-US/", R.drawable.firefox));
        if(itemTitle != null && itemDescription != null)
            myList.add(new RowModel(itemTitle , itemDescription, R.drawable.not_found));


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
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(getApplicationContext(), "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(), "onSaveInstanceState" + lastClicked, Toast.LENGTH_SHORT).show();
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
            case R.id.option2:
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
