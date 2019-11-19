package com.jeanboy.app.pokemonlayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeanboy.app.pokemonlayout.layout.FooterLayout;
import com.jeanboy.app.pokemonlayout.layout.HeaderLayout;
import com.jeanboy.app.pokemonlayout.layout.MaskLayout;
import com.jeanboy.app.pokemonlayout.listener.OnLoadListener;
import com.jeanboy.app.pokemonlayout.listener.OnLoadMoreListener;
import com.jeanboy.app.pokemonlayout.listener.OnRefreshListener;

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
        testAdapter.setHeaderLayout(new HeaderLayout());
        testAdapter.setFooterLayout(new FooterLayout());
        testAdapter.setMaskLayout(new MaskLayout());
        testAdapter.setOnRefreshListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                currentPage = 1;
                refreshData();
            }
        });

        testAdapter.setOnLoadMoreListener(rv_container, new OnLoadListener() {
            @Override
            public void onLoad() {
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
        loadData();
    }

    private void loadData() {
        testAdapter.setLoading();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(MainActivity.class.getSimpleName(), "====currentPage======" + currentPage);
                if (currentPage == 0) {
                    testAdapter.setLoadCompleted(false);
                } else if (currentPage == 1) {
                    for (int i = 0; i < 20; i++) {
                        dataList.add("===" + i);
                    }
                    testAdapter.setLoadCompleted(true);
                } else if (currentPage == 2) {
                    testAdapter.setLoadError();
                } else if (currentPage == 3) {
                    for (int i = 0; i < 5; i++) {
                        dataList.add("===" + i);
                    }
                    testAdapter.setLoadCompleted(true);
                } else {
                    testAdapter.setLoadCompleted(false);
                }
                currentPage++;
            }
        }, 2000);

    }
}
