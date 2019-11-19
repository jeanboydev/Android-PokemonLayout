package com.jeanboy.app.pokemonlayout.layout;

import android.util.Log;

import com.jeanboy.app.pokemonlayout.R;
import com.jeanboy.app.pokemonlayout.base.BaseViewHolder;
import com.jeanboy.app.pokemonlayout.base.PokemonLayout;
import com.jeanboy.app.pokemonlayout.constants.ViewState;
import com.jeanboy.app.pokemonlayout.listener.OnLoadListener;

/**
 * @Synopsis
 * @Author caojianbo
 * @Date 2019/11/18 21:02
 */
public class MaskLayout extends PokemonLayout {

    @Override
    public int getLayoutId() {
        return R.layout.item_mask;
    }

    @Override
    public void convert(BaseViewHolder holder, int state, OnLoadListener listener) {
        switch (state) {
            case ViewState.STATE_LOADING:
                Log.e(MaskLayout.class.getSimpleName(), "========convertMask=========STATE_LOADING=");
                holder.setVisible(R.id.pb_progress, true);
                holder.setVisible(R.id.iv_icon, false);
                holder.setText(R.id.tv_text, "加载中...");
                break;
            case ViewState.STATE_EMPTY:
                Log.e(MaskLayout.class.getSimpleName(), "========convertMask=========STATE_EMPTY=");
                holder.setVisible(R.id.pb_progress, false);
                holder.setVisible(R.id.iv_icon, true);
                holder.setText(R.id.tv_text, "数据为空，点击重新加载");
                convertListener(holder.itemView, listener);
                break;
            case ViewState.STATE_ERROR:
                Log.e(MaskLayout.class.getSimpleName(), "========convertMask=========STATE_ERROR=");
                holder.setVisible(R.id.pb_progress, false);
                holder.setVisible(R.id.iv_icon, true);
                holder.setText(R.id.tv_text, "加载失败，点击重试");
                convertListener(holder.itemView, listener);
                break;
        }
    }
}
