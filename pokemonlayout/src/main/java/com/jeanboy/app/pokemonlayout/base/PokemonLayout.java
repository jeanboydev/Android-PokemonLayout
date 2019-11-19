package com.jeanboy.app.pokemonlayout.base;

import android.view.View;

import com.jeanboy.app.pokemonlayout.listener.OnLoadListener;

/**
 * @Synopsis
 * @Author caojianbo
 * @Date 2019/11/18 20:49
 */
public abstract class PokemonLayout {

    protected void convertListener(View view, final OnLoadListener listener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLoad();
                }
            }
        });
    }

    public abstract int getLayoutId();

    public abstract void convert(BaseViewHolder holder, int state, OnLoadListener listener);
}
