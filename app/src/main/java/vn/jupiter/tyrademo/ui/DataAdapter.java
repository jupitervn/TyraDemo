package vn.jupiter.tyrademo.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public abstract class DataAdapter<DATA> extends RecyclerView.Adapter<BindableVH<DATA>> {

    private List<DATA> items = new ArrayList<>();
    protected LayoutInflater layoutInflater;

    public DataAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    public void onBindViewHolder(BindableVH<DATA> holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<DATA> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public DATA getItemAtPosition(int position) {
        return items.get(position);
    }
}
