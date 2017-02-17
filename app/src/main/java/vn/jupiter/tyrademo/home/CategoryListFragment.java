package vn.jupiter.tyrademo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.jupiter.tyrademo.FragmentListWithSearch;
import vn.jupiter.tyrademo.ListViewWithSearchPresenter;
import vn.jupiter.tyrademo.R;
import vn.jupiter.tyrademo.ui.DataAdapter;

public class CategoryListFragment extends FragmentListWithSearch<CategoryPresentationModel> {

    public static CategoryListFragment newInstance() {
        Bundle args = new Bundle();
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Dropbox");
    }

    @Override
    protected DataAdapter createAdapter() {
        return new DataAdapter<CategoryPresentationModel>(LayoutInflater.from(getContext())) {
            @Override
            public CategoryBindableVH onCreateViewHolder(ViewGroup parent, int viewType) {
                return new CategoryBindableVH(layoutInflater.inflate(R.layout.item_category, parent, false));
            }
        };
    }

    @Override
    protected ListViewWithSearchPresenter createPresenter() {
        return new CategoryListPresenter(this);
    }

    @Override
    public boolean performItemClick(RecyclerView parent, View view, int position, long id) {
        CategoryPresentationModel category = adapter.getItemAtPosition(position);
        appNavigator.openCategoryDetail(category);
        return true;
    }
}
