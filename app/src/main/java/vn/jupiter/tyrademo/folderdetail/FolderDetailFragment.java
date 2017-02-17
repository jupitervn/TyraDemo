package vn.jupiter.tyrademo.folderdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import vn.jupiter.tyrademo.ListViewWithSearchPresenter;
import vn.jupiter.tyrademo.categorydetail.CategoryDetailFragment;
import vn.jupiter.tyrademo.categorydetail.FolderPresentationModel;

public class FolderDetailFragment extends CategoryDetailFragment {

    public static final String ARG_FOLDER_ID = "arg_folder_id";
    public static final String ARG_FOLDER_NAME = "arg_folder_name";

    public static FolderDetailFragment newInstance(FolderPresentationModel folder) {
        Bundle args = new Bundle();
        args.putString(ARG_FOLDER_ID, folder.getFolderId());
        args.putString(ARG_FOLDER_NAME, folder.getName());

        FolderDetailFragment fragment = new FolderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String folderName = getArguments().getString(ARG_FOLDER_NAME);
        getActivity().setTitle(folderName);
    }

    @Override
    protected ListViewWithSearchPresenter createPresenter() {
        String folderId = getArguments().getString(ARG_FOLDER_ID);
        return new FolderDetailPresenter(folderId, this);
    }
}
