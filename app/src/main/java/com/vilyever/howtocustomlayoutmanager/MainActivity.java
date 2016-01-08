package com.vilyever.howtocustomlayoutmanager;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /** Convenience Var to call this */
    final MainActivity self = this;

    private RecyclerView recyclerView;
    public RecyclerView getRecyclerView() { if (recyclerView == null) { recyclerView = (RecyclerView) self.findViewById(R.id.recyclerView); } return recyclerView; }

    private CustomLayoutManager layoutManager;
    public CustomLayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new CustomLayoutManager(CustomLayoutManager.HORIZONTAL, 5);
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
            dataAdapter.setDelegate(new DataAdapter.Delegate() {
                @Override
                public void onItemClick(DataAdapter adapter, int position) {
                    Snackbar.make(self.getRecyclerView(),
                            "Item " + position + " clicked. " + self.getDataAdapter().getDemoModels().get(position).getPreferWidth() + "x" + self.getDataAdapter().getDemoModels().get(position).getPreferHeight(),
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    self.getDataAdapter().updateModel(position, DemoModel.generateDemoModel());

                    self.getLayoutManager().setCanScrollHorizontal(false);
                }
            });
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
        self.getRecyclerView().getItemAnimator().setAddDuration(500);
        self.getRecyclerView().getItemAnimator().setChangeDuration(500);
        self.getRecyclerView().getItemAnimator().setMoveDuration(500);
        self.getRecyclerView().getItemAnimator().setRemoveDuration(500);
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

        /**
         * copy the NumberPickerDialog from https://github.com/devunwired/recyclerview-playground/blob/master/app/src/main/java/com/example/android/recyclerplayground/NumberPickerDialog.java
         */
        NumberPickerDialog dialog;
        switch (item.getItemId()) {
            case R.id.action_add:
                dialog = new NumberPickerDialog(self);
                dialog.setTitle("Position to Add");
                dialog.setPickerRange(0, self.getDataAdapter().getItemCount());
                dialog.setOnNumberSelectedListener(new NumberPickerDialog.OnNumberSelectedListener() {
                    @Override
                    public void onNumberSelected(int value) {
                        self.getDataAdapter().addModel(value, DemoModel.generateDemoModel());
                    }
                });
                dialog.show();

                return true;
            case R.id.action_remove:
                dialog = new NumberPickerDialog(self);
                dialog.setTitle("Position to Remove");
                dialog.setPickerRange(0, self.getDataAdapter().getItemCount() - 1);
                dialog.setOnNumberSelectedListener(new NumberPickerDialog.OnNumberSelectedListener() {
                    @Override
                    public void onNumberSelected(int value) {
                        self.getDataAdapter().removeModel(value);
                    }
                });
                dialog.show();

                return true;
            case R.id.action_empty:
                self.getDataAdapter().clear();
                return true;
            case R.id.action_small:
                self.getDemoModels().clear();
                for (int i = 0; i < 5; i++) {
                    self.getDemoModels().add(DemoModel.generateDemoModel());
                }
                self.getDataAdapter().setDemoModels(self.getDemoModels());
                return true;
            case R.id.action_medium:
                self.getDemoModels().clear();
                for (int i = 0; i < 50; i++) {
                    self.getDemoModels().add(DemoModel.generateDemoModel());
                }
                self.getDataAdapter().setDemoModels(self.getDemoModels());
                return true;
            case R.id.action_large:
                self.getDemoModels().clear();
                for (int i = 0; i < 100; i++) {
                    self.getDemoModels().add(DemoModel.generateDemoModel());
                }
                self.getDataAdapter().setDemoModels(self.getDemoModels());
                return true;
            case R.id.action_scroll_to_position:
                dialog = new NumberPickerDialog(self);
                dialog.setTitle("Position to Scroll");
                dialog.setPickerRange(0, self.getDataAdapter().getItemCount() - 1);
                dialog.setOnNumberSelectedListener(new NumberPickerDialog.OnNumberSelectedListener() {
                    @Override
                    public void onNumberSelected(int value) {
                        self.getRecyclerView().scrollToPosition(value);
                    }
                });
                dialog.show();

                return true;
            case R.id.action_smooth_to_position:
                dialog = new NumberPickerDialog(self);
                dialog.setTitle("Position to Smooth Scroll");
                dialog.setPickerRange(0, self.getDataAdapter().getItemCount() - 1);
                dialog.setOnNumberSelectedListener(new NumberPickerDialog.OnNumberSelectedListener() {
                    @Override
                    public void onNumberSelected(int value) {
                        self.getRecyclerView().smoothScrollToPosition(value);
                    }
                });
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
