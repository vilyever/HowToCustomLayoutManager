package com.vilyever.howtocustomlayoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /**
     * this的便捷访问
     */
    final MainActivity self = this;

    private RecyclerView recyclerView;
    public RecyclerView getRecyclerView() { if (recyclerView == null) { recyclerView = (RecyclerView) self.findViewById(R.id.recyclerView); } return recyclerView; }

    private LinearLayoutManager layoutManager;
    public LinearLayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(self);
        }
        return layoutManager;
    }

    private ArrayList<DemoModel> demoModels;
    public ArrayList<DemoModel> getDemoModels() {
        if (demoModels == null) {
            demoModels = DemoModel.generateDemoList();
        }
        return demoModels;
    }

    private DataAdapter dataAdapter;
    public DataAdapter getDataAdapter() {
        if (dataAdapter == null) {
            dataAdapter = new DataAdapter();
        }
        return dataAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        self.getRecyclerView().setLayoutManager(self.getLayoutManager());
        self.getRecyclerView().setAdapter(self.getDataAdapter());
        self.getDataAdapter().setDemoModels(self.getDemoModels());
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
}
