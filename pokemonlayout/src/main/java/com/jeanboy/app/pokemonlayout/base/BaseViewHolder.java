package com.jeanboy.app.pokemonlayout.base;

import android.content.Context;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Synopsis BaseViewHolder
 * @Author caojianbo
 * @Date 2019/11/5 18:36
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    public Context getContext() {
        return itemView.getContext();
    }

    public Resources getResources() {
        return itemView.getResources();
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
