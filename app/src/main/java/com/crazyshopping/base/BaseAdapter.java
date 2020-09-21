package com.crazyshopping.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    public Context context;
    List<T> data;
  IClick callback;

    public void setCallback(IClick callback) {
        this.callback = callback;
    }

    public BaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getLayout(), parent, false);
        BaseViewHolder vh = new BaseViewHolder(view);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback!=null){
                    callback.click(vh.getLayoutPosition());
                }
            }
        });
        return vh;
    }

    protected abstract int getLayout();


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder vh = (BaseViewHolder) holder;
        T _data = data.get(position);
        bindData(vh,_data,position);
    }

    protected abstract void bindData(BaseViewHolder vh, T data, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface IClick{
        void click(int pos);
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{
        //性能优化
        SparseArray views = new SparseArray();

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        //通过id获取支配器的组件
        public View getViewById(int id){
            View view = (View) views.get(id);
            if (view==null){
                view = itemView.findViewById(id);
                views.append(id,view);
            }
            return view;
        }
    }
}
