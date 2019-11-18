package com.jeanboy.app.pokemonlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.os.Handler;

import com.jeanboy.app.pokemonlayout.base.OnLoadListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> dataList = new ArrayList<>();
    private RecyclerView rv_container;
    private TestAdapter testAdapter;
    private static final int PAGE_SIZE = 10;
    private int currentPage = 0;

    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_container = findViewById(R.id.rv_container);
        testAdapter = new TestAdapter(dataList);
        testAdapter.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                refreshData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_container.setLayoutManager(layoutManager);
        rv_container.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv_container.setAdapter(testAdapter);
        refreshData();
    }

    private void refreshData() {
//        currentPage = 0;
        testAdapter.setLoading(false);
        loadData();
    }

    private void loadData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPage == 0) {
                    testAdapter.setLoadCompleted(currentPage >= 1, true);
//                } else if (currentPage == 1) {
//                    for (int i = 0; i < 5; i++) {
//                        dataList.add("===" + i);
//                    }
//                    testAdapter.setLoadCompleted(currentPage >= 1, false);
                } else if (currentPage < 3) {
                    for (int i = 0; i < 20; i++) {
                        dataList.add("===" + i);
                    }
                    testAdapter.setLoadCompleted(currentPage >= 1, false);
                } else {
                    testAdapter.setLoadCompleted(currentPage >= 1, true);
                }
                currentPage++;
            }
        }, 2000);

    }
}
