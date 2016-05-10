package com.jason.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jason.rxjavademo.activity.AppInfoActivity;
import com.jason.rxjavademo.activity.MapScanActivity;
import com.jason.rxjavademo.activity.TextSearchActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    public final static String LOG_TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({
            R.id.btnAppInfo,
            R.id.btnAppInfoFilter,
            R.id.btnDebounce,
            R.id.btnMapScan
    })
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnAppInfo:
                intent = new Intent(this, AppInfoActivity.class);
                intent.putExtra("type", "create");
                startActivity(intent);
                break;
            case R.id.btnAppInfoFilter:
                intent = new Intent(this, AppInfoActivity.class);
                intent.putExtra("type", "filter");
                startActivity(intent);
                break;
            case R.id.btnDebounce:
                intent = new Intent(this, TextSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMapScan:
                intent = new Intent(this, MapScanActivity.class);
                startActivity(intent);
                break;

        }
    }

}
