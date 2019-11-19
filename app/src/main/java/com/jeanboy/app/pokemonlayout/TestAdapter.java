package com.jeanboy.app.pokemonlayout;

import android.util.Log;

import com.jeanboy.app.pokemonlayout.base.BaseViewHolder;

import java.util.List;

/**
 * @Synopsis
 * @Author caojianbo
 * @Date 2019/11/18 12:20
 */
public class TestAdapter extends PokemonAdapter<String> {

    public TestAdapter(List<String> dataList) {
        super(dataList, R.layout.item_cell);
    }

    @Override
    public void convert(BaseViewHolder holder, String s, int position) {
        Log.e(TestAdapter.class.getSimpleName(), "========convert=========");
        holder.setText(R.id.tv_data, s);
    }
}
