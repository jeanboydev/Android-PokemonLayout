package com.jeanboy.app.pokemonlayout;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeanboy.app.pokemonlayout.base.BaseViewHolder;
import com.jeanboy.app.pokemonlayout.base.RecyclerViewBaseAdapter;
import com.jeanboy.app.pokemonlayout.constants.ViewState;
import com.jeanboy.app.pokemonlayout.constants.ViewType;
import com.jeanboy.app.pokemonlayout.base.PokemonLayout;
import com.jeanboy.app.pokemonlayout.helper.RecyclerViewHelper;
import com.jeanboy.app.pokemonlayout.listener.OnLoadListener;

import java.util.List;

/**
 * @Synopsis
 * @Author caojianbo
 * @Date 2019/11/16 18:24
 */
public abstract class PokemonAdapter<T> extends RecyclerViewBaseAdapter<T> {

    private int currentState;

    private PokemonLayout headerLayout;
    private PokemonLayout footerLayout;
    private PokemonLayout maskLayout;

    private OnLoadListener onRefreshListener;
    private OnLoadListener onLoadMoreListener;

    public PokemonAdapter(List<T> dataList, int itemLayoutId) {
        super(dataList, itemLayoutId);
    }

    public void setHeaderLayout(PokemonLayout headerLayout) {
        this.headerLayout = headerLayout;
    }

    public void setFooterLayout(PokemonLayout footerLayout) {
        this.footerLayout = footerLayout;
    }

    public void setMaskLayout(PokemonLayout maskLayout) {
        this.maskLayout = maskLayout;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType.TYPE_HEADER:
                return new BaseViewHolder(getLayoutView(parent, headerLayout.getLayoutId()));
            case ViewType.TYPE_FOOTER:
                return new BaseViewHolder(getLayoutView(parent, footerLayout.getLayoutId()));
            case ViewType.TYPE_MASK:
                return new BaseViewHolder(getLayoutView(parent, maskLayout.getLayoutId()));
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ViewType.TYPE_HEADER:
                headerLayout.convert(holder, currentState, null);
                break;
            case ViewType.TYPE_FOOTER:
                footerLayout.convert(holder, currentState, onLoadMoreListener);
                break;
            case ViewType.TYPE_MASK:
                maskLayout.convert(holder, currentState, onRefreshListener);
                break;
            default:
                super.onBindViewHolder(holder, position - getHeaderCount());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (super.getItemCount() == 0 && getMaskCount() > 0) {
            return ViewType.TYPE_MASK;
        }

        int headerCount = getHeaderCount();
        if (headerCount > 0 && position < headerCount) {
            return ViewType.TYPE_HEADER;
        }

        int footerCount = getFooterCount();
        if (footerCount > 0 && position >= footerCount + super.getItemCount()) {
            return ViewType.TYPE_FOOTER;
        }
        return super.getItemViewType(position - headerCount);
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount() == 0) {
            return getMaskCount();
        }

        if (super.getItemCount() > 0 && ViewState.STATE_COMPLETED == currentState) {
            return getHeaderCount() + super.getItemCount();
        } else {
            return getHeaderCount() + getFooterCount() + super.getItemCount();
        }
    }

    @SuppressLint("ResourceType")
    private int getViewCount(@LayoutRes int layoutId) {
        return layoutId > 0 ? 1 : 0;
    }

    private int getHeaderCount() {
        if (headerLayout == null) return 0;
        return getViewCount(headerLayout.getLayoutId());
    }

    private int getFooterCount() {
        if (footerLayout == null) return 0;
        return getViewCount(footerLayout.getLayoutId());
    }

    private int getMaskCount() {
        if (maskLayout == null) return 0;
        return getViewCount(maskLayout.getLayoutId());
    }


    public void setLoading() {
        currentState = ViewState.STATE_LOADING;
        notifyDataSetChanged();
    }

    public void setLoadError() {
        currentState = ViewState.STATE_ERROR;
        notifyDataSetChanged();
    }

    public void setLoadCompleted(boolean hasMore) {
        if (super.getItemCount() == 0) {
            currentState = hasMore ? ViewState.STATE_DATA : ViewState.STATE_EMPTY;
        } else {
            currentState = hasMore ? ViewState.STATE_DATA : ViewState.STATE_COMPLETED;
        }
        notifyDataSetChanged();
    }

    public void setOnRefreshListener(OnLoadListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setOnLoadMoreListener(RecyclerView recyclerView, final OnLoadListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        RecyclerViewHelper.addVerticalWatcher(recyclerView, new RecyclerViewHelper.BottomWatcher() {
            @Override
            public void onBottom() {
                if (ViewState.STATE_COMPLETED == currentState) return;
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoad();
                }
            }
        });
    }
}
