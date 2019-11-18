package com.jeanboy.app.pokemonlayout;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeanboy.app.pokemonlayout.base.BaseViewHolder;
import com.jeanboy.app.pokemonlayout.base.OnLoadMoreListener;
import com.jeanboy.app.pokemonlayout.base.OnRefreshListener;
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
        if (!isLoadMore) {
            dataList.clear();
        }
        notifyDataSetChanged();
    }

    public void setLoadError(boolean isLoadMore) {
        currentState = ViewConstants.STATE_ERROR;
        if (!isLoadMore) {
            dataList.clear();
        }
        notifyDataSetChanged();
    }

    public void setLoadEmpty(boolean isLoadMore) {
        currentState = ViewConstants.STATE_EMPTY;
        if (!isLoadMore) {
            dataList.clear();
        }
        notifyDataSetChanged();
    }

    public void setLoadCompleted(boolean isLoadMore) {
        currentState = ViewConstants.STATE_COMPLETED;
        if (!isLoadMore) {
            dataList.clear();
        }
        notifyDataSetChanged();
    }


    protected void convertListener(@NonNull View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            }
        });
    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    private OnLoadMoreListener onLoadMoreListener;

    public void setOnLoadMoreListener(RecyclerView recyclerView, final OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isNotHeader = recyclerView.canScrollVertically(-1);
                boolean isNotBottom = recyclerView.canScrollVertically(1);
                Log.e(PokemonAdapter.class.getSimpleName(), "==isNotHeader==" + isNotHeader + "==isNotBottom==" + isNotBottom);
                if (!isNotBottom) {
                    Log.e(PokemonAdapter.class.getSimpleName(), "====load more===");
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
