package com.example.echoapp.activities;

import static com.example.echoapp.lib.Utils.showInfoDialog;
import static com.example.echoapp.model.UserEntryList.getEchoListObjectFromJSON;
import static com.example.echoapp.model.UserEntryList.getJSONStringFromEchoListObject;

import android.os.Bundle;

import com.example.echoapp.R;
import com.example.echoapp.lib.Utils;
import com.example.echoapp.model.UserEntryList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.echoapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private EditText et;
    private TextView tv1, tv2,  tv3, tv4;
    private Snackbar mSnackBar;
    private UserEntryList userEntryList;
    private boolean mShowHistory;
    private String mKEY_STRING;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar();
        setFab();
        setupFields();
    }


    private void setToolbar() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    }

    private void setFab() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFABClick();
            }
        });
    }

    private void setupFields() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        et =   findViewById(R.id.et);
        View layoutMain = findViewById(R.id.main_activity);
        mSnackBar = Snackbar.make(layoutMain, "", Snackbar.LENGTH_INDEFINITE);

        userEntryList = new UserEntryList();
        mShowHistory = true;
    }

    private void showHistory() {

        if(!mShowHistory) {

            tv4.setText(userEntryList.getLastUserEntry());
        }

        else {

            tv4.setText(userEntryList.getUserEntriesListAsString());
        }
    }

    private void handleFABClick() {

        String value = et.getText().toString();
        tv2.setText("Echo Says: " + value);

        userEntryList.addEntryToList(value);
        showHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.action_about) {
                showAbout();
                return true;
            }
            else if (itemId == R.id.action_toggle_display_entries) {
                toggleMenuItem(item);
                mShowHistory = item.isChecked();
                showHistory();
                return true;}

            else if (itemId == R.id.click_icon_clear_entries) {
                toggleMenuItem(item);
                clearHistory();
                return true;}

            return super.onOptionsItemSelected(item);

        }

        private void clearHistory() {

            userEntryList.clearUserEntries();
            tv4.setText("");
        }

            private void toggleMenuItem(MenuItem item) {
                item.setChecked(!item.isChecked());
            }

    private void showAbout() {
        Utils.showInfoDialog (MainActivity.this,
                R.string.about_menu_title, R.string.about_message);
    }

    //TODO this method and OnResore(), Layout view as well, splash activity
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // call the super-class's method to save fields, etc.
        super.onSaveInstanceState(outState);
        outState.putString(mKEY_STRING, getJSONStringFromEchoListObject(userEntryList));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userEntryList = getEchoListObjectFromJSON(savedInstanceState.getString(mKEY_STRING));

    }





    @Override  public boolean  onPrepareOptionsMenu (Menu menu)
    {
        menu.findItem (R.id.action_toggle_display_entries).setChecked (mShowHistory);
        return super.onPrepareOptionsMenu (menu);
    }

}
