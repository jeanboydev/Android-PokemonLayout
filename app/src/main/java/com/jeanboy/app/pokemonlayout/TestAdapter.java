package com.jeanboy.app.pokemonlayout;

import android.util.Log;

import com.jeanboy.app.pokemonlayout.base.BaseViewHolder;
import com.jeanboy.app.pokemonlayout.base.ViewConstants;

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
    public int getHeaderLayout() {
        return R.layout.item_header;
    }

    @Override
    public int getFooterLayout() {
        return R.layout.item_footer;
    }

    @Override
    public int getMaskLayout() {
        return R.layout.item_mask;
    }

    @Override
    public void convertHeader(BaseViewHolder holder) {
        Log.e(TestAdapter.class.getSimpleName(), "========convertHeader=========");
    }

    @Override
    public void convertFooter(BaseViewHolder holder, int state) {
        Log.e(TestAdapter.class.getSimpleName(), "========convertFooter========state=" + state);
        switch (state) {
            case ViewConstants.STATE_LOADING:
                Log.e(TestAdapter.class.getSimpleName(), "========convertMask=========STATE_LOADING=");
                holder.setVisible(R.id.pb_progress, true);
                holder.setVisible(R.id.iv_icon, false);
                holder.setText(R.id.tv_text, "加载中...");
                break;
            case ViewConstants.STATE_EMPTY:
                Log.e(TestAdapter.class.getSimpleName(), "========convertMask=========STATE_EMPTY=");
                holder.setVisible(R.id.pb_progress, false);
                holder.setVisible(R.id.iv_icon, true);
                holder.setText(R.id.tv_text, "数据为空，点击重试");
                break;
            case ViewConstants.STATE_ERROR:
                Log.e(TestAdapter.class.getSimpleName(), "========convertMask=========STATE_ERROR=");
                holder.setVisible(R.id.pb_progress, false);
                holder.setVisible(R.id.iv_icon, true);
                holder.setText(R.id.tv_text, "加载失败，点击重试");
                break;
        }
    }

    @Override
    public void convertMask(BaseViewHolder holder, int state) {
        Log.e(TestAdapter.class.getSimpleName(), "========convertMask=========state=" + state);
        switch (state) {
            case ViewConstants.STATE_LOADING:
                Log.e(TestAdapter.class.getSimpleName(), "========convertMask=========STATE_LOADING=");
                holder.setVisible(R.id.pb_progress, true);
                holder.setVisible(R.id.iv_icon, false);
                holder.setText(R.id.tv_text, "加载中...");
                break;
            case ViewConstants.STATE_EMPTY:
                Log.e(TestAdapter.class.getSimpleName(), "========convertMask=========STATE_EMPTY=");
                holder.setVisible(R.id.pb_progress, false);
                holder.setVisible(R.id.iv_icon, true);
                holder.setText(R.id.tv_text, "数据为空，点击重试");
                convertListener(holder.itemView);
                break;
            case ViewConstants.STATE_ERROR:
                Log.e(TestAdapter.class.getSimpleName(), "========convertMask=========STATE_ERROR=");
                holder.setVisible(R.id.pb_progress, false);
                holder.setVisible(R.id.iv_icon, true);
                holder.setText(R.id.tv_text, "加载失败，点击重试");
                convertListener(holder.itemView);
                break;
        }
    }

    @Override
    public void convert(BaseViewHolder holder, String s, int position) {
        Log.e(TestAdapter.class.getSimpleName(), "========convert=========");
        holder.setText(R.id.tv_data, s);
    }
}
