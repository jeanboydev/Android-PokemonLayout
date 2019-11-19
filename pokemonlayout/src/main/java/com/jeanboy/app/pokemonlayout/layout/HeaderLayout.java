package com.jeanboy.app.pokemonlayout.layout;

import com.jeanboy.app.pokemonlayout.R;
import com.jeanboy.app.pokemonlayout.base.BaseViewHolder;
import com.jeanboy.app.pokemonlayout.base.PokemonLayout;
import com.jeanboy.app.pokemonlayout.listener.OnLoadListener;

/**
 * @Synopsis
 * @Author caojianbo
 * @Date 2019/11/18 21:00
 */
public class HeaderLayout extends PokemonLayout {

    @Override
    public int getLayoutId() {
        return R.layout.item_header;
    }

    @Override
    public void convert(BaseViewHolder holder, int state, OnLoadListener onLoadMoreListener) {

    }
}
