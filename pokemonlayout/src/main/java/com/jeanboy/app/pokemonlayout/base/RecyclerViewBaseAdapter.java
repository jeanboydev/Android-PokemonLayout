package com.jeanboy.app.pokemonlayout.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @Synopsis RecyclerViewBaseAdapter
 * @Author caojianbo
 * @Date 2019/11/5 18:38
 */
public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> dataList;
    private int itemLayoutId;

    public RecyclerViewBaseAdapter(List<T> dataList, @LayoutRes int itemLayoutId) {
        this.dataList = dataList;
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(getLayoutView(parent, itemLayoutId));
        setItemClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        convert(holder, dataList.get(position), holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected View getLayoutView(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    private void setItemClickListener(final BaseViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    onItemClickListener.onItemClick(v, holder, position);
                }
            }
        });
    }


    public abstract void convert(BaseViewHolder holder, T t, int position);

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, BaseViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
