package com.jeanboy.app.pokemonlayout;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.jeanboy.app.pokemonlayout.base.BaseViewHolder;
import com.jeanboy.app.pokemonlayout.base.OnLoadListener;
import com.jeanboy.app.pokemonlayout.base.RecyclerViewBaseAdapter;
import com.jeanboy.app.pokemonlayout.base.ViewConstants;

import java.util.List;

/**
 * @Synopsis
 * @Author caojianbo
 * @Date 2019/11/16 18:24
 */
public abstract class PokemonAdapter<T> extends RecyclerViewBaseAdapter<T> {

    private int currentState;

    public PokemonAdapter(List<T> dataList, int itemLayoutId) {
        super(dataList, itemLayoutId);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewConstants.TYPE_HEADER:
                return new BaseViewHolder(getLayoutView(parent, getHeaderLayout()));
            case ViewConstants.TYPE_FOOTER:
                return new BaseViewHolder(getLayoutView(parent, getFooterLayout()));
            case ViewConstants.TYPE_MASK:
                return new BaseViewHolder(getLayoutView(parent, getMaskLayout()));
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ViewConstants.TYPE_HEADER:
                convertHeader(holder);
                break;
            case ViewConstants.TYPE_FOOTER:
                convertFooter(holder, currentState);
                break;
            case ViewConstants.TYPE_MASK:
                convertMask(holder, currentState);
                break;
            default:
                super.onBindViewHolder(holder, position - getViewCount(getHeaderLayout()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (super.getItemCount() == 0 && getViewCount(getMaskLayout()) > 0) {
            return ViewConstants.TYPE_MASK;
        }

        int headerCount = getViewCount(getHeaderLayout());
        if (headerCount > 0 && position < headerCount) {
            return ViewConstants.TYPE_HEADER;
        }

        int footerCount = getViewCount(getFooterLayout());
        if (footerCount > 0 && position >= footerCount + super.getItemCount()) {
            return ViewConstants.TYPE_FOOTER;
        }
        return super.getItemViewType(position - headerCount);
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount() == 0) {
            return getViewCount(getMaskLayout());
        }

        if (super.getItemCount() > 0 && ViewConstants.STATE_EMPTY == currentState) {
            return getViewCount(getHeaderLayout()) + super.getItemCount();
        } else {
            return getViewCount(getHeaderLayout()) + getViewCount(getFooterLayout()) + super.getItemCount();
        }
    }

    @SuppressLint("ResourceType")
    private int getViewCount(@LayoutRes int layoutId) {
        return layoutId > 0 ? 1 : 0;
    }

    public int getHeaderLayout() {
        return 0;
    }

    public int getFooterLayout() {
        return 0;
    }

    public int getMaskLayout() {
        return 0;
    }

    public void convertHeader(BaseViewHolder holder) {
    }

    public void convertFooter(BaseViewHolder holder, int state) {
    }

    public void convertMask(BaseViewHolder holder, int state) {
    }

    public void setLoading(boolean isLoadMore) {
        currentState = ViewConstants.STATE_LOADING;
        updateItemSate(isLoadMore);
    }

    public void setLoadError(boolean isLoadMore) {
        currentState = ViewConstants.STATE_ERROR;
        updateItemSate(isLoadMore);
    }

    public void setLoadCompleted(boolean isLoadMore, boolean isEmpty) {
        currentState = isEmpty ? ViewConstants.STATE_EMPTY : ViewConstants.STATE_COMPLETED;
        updateItemSate(isLoadMore);
    }

    private void updateItemSate(boolean isLoadMore) {
        if (isLoadMore) {
            notifyDataSetChanged();
//            notifyItemChanged(getItemCount() - 1);
        } else {
            dataList.clear();
            notifyDataSetChanged();
        }
    }

    protected void convertListener(@NonNull View view, final boolean isRefresh) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLoadListener != null) {
                    if (isRefresh) {
                        onLoadListener.onRefresh();
                    } else {
                        onLoadListener.onLoadMore();
                    }
                }
            }
        });
    }

    private OnLoadListener onLoadListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }
}
