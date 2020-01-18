package com.example.sorting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ExampleItem> mExampleList;
    private ExampleAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();

        Button buttonSort = findViewById(R.id.button_sort);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortArrayList();
            }
        });
    }

    private void sortArrayList() {
        Collections.sort(mExampleList, new Comparator<ExampleItem>() {
            @Override
            public int compare(ExampleItem o1, ExampleItem o2) {
                return o1.getText2().compareTo(o2.getText2());
            }
        });

        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void createExampleList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem("Daryl", "Dixon"));
        mExampleList.add(new ExampleItem("Rick", "Grimes"));
        mExampleList.add(new ExampleItem("Abraham", "Ford"));
        mExampleList.add(new ExampleItem("Eugene", "Porter"));
    }

    private void buildRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewAdapter = new ExampleAdapter(mExampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
