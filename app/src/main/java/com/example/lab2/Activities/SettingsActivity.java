package com.example.lab2.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lab2.Fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_PREF_SWITCH = "switch_preference_1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        boolean switchPref = sharedPref.getBoolean(
                SettingsActivity.KEY_PREF_SWITCH, false);

        Toast.makeText(this, (switchPref?"TRUE":"FALSE"), Toast.LENGTH_SHORT).show();
    }
}
