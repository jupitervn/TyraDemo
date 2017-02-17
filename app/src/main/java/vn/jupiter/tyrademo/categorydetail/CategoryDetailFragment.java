package vn.jupiter.tyrademo.categorydetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.jupiter.tyrademo.FragmentListWithSearch;
import vn.jupiter.tyrademo.ListViewWithSearchPresenter;
import vn.jupiter.tyrademo.R;
import vn.jupiter.tyrademo.data.FileType;
import vn.jupiter.tyrademo.home.CategoryPresentationModel;
import vn.jupiter.tyrademo.ui.BindableVH;
import vn.jupiter.tyrademo.ui.DataAdapter;

public class CategoryDetailFragment extends FragmentListWithSearch<FileType> {

    public static final String ARG_CATEGORY_TYPE = "arg_category_type";
    public static final String ARG_CATEGORY_NAME = "arg_category_name";

    public static CategoryDetailFragment newInstance(CategoryPresentationModel category) {
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_TYPE, category.getType());
        args.putString(ARG_CATEGORY_NAME, category.getName());
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getString(ARG_CATEGORY_NAME));
    }

    @Override
    protected DataAdapter<FileType> createAdapter() {
        return new DataAdapter<FileType>(LayoutInflater.from(getContext())) {
            @Override
            public BindableVH onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == R.layout.item_folder) {
                    return new FolderBindableVH(layoutInflater.inflate(viewType, parent, false));
                } else {
                    return new FileBindableVH(layoutInflater.inflate(viewType, parent, false));
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (getItemAtPosition(position) instanceof FolderPresentationModel) {
                    return R.layout.item_folder;
                } else {
                    return R.layout.item_file;
                }
            }
        };
    }

    @Override
    protected ListViewWithSearchPresenter createPresenter() {
        int categoryType = getArguments().getInt(ARG_CATEGORY_TYPE);
        return new CategoryDetailPresenter(categoryType, this);
    }

    @Override
    public boolean performItemClick(RecyclerView parent, View view, int position, long id) {
        FileType fileType = adapter.getItemAtPosition(position);
        if (fileType instanceof FolderPresentationModel) {
            appNavigator.openFolder(((FolderPresentationModel) fileType));
        } else if (fileType instanceof DocumentPresentationModel) {
            appNavigator.openDocument(((DocumentPresentationModel) fileType));
        }
        return true;
    }
}
