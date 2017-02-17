package vn.jupiter.tyrademo.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BindableVH<DATA> extends RecyclerView.ViewHolder {

    public BindableVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void bind(DATA data);

}
