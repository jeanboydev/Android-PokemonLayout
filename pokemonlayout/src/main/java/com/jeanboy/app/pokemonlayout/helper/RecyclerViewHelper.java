package com.jeanboy.app.pokemonlayout.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Synopsis
 * @Author caojianbo
 * @Date 2019/11/19 10:11
 */
public class RecyclerViewHelper {

    public interface BottomWatcher {
        void onBottom();
    }

    public static void addVerticalWatcher(RecyclerView recyclerView, final BottomWatcher watcher) {
        if (recyclerView == null) return;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int HEADER = -1;
            private static final int BOTTOM = 1;
            private int scrollState;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollState = newState;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecyclerView.SCROLL_STATE_IDLE == scrollState) return;
                if (recyclerView.canScrollVertically(BOTTOM)) return;
                if (watcher != null) {
                    watcher.onBottom();
                }
            }
        });
    }
}
