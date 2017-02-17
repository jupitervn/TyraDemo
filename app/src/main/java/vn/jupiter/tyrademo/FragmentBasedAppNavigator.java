package vn.jupiter.tyrademo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import vn.jupiter.tyrademo.categorydetail.CategoryDetailFragment;
import vn.jupiter.tyrademo.categorydetail.DocumentPresentationModel;
import vn.jupiter.tyrademo.categorydetail.FolderPresentationModel;
import vn.jupiter.tyrademo.documentdetail.DocumentDetailActivity;
import vn.jupiter.tyrademo.folderdetail.FolderDetailFragment;
import vn.jupiter.tyrademo.home.CategoryListFragment;
import vn.jupiter.tyrademo.home.CategoryPresentationModel;

public class FragmentBasedAppNavigator implements AppNavigator {

    private FragmentActivity activity;
    private int fragmentContentId;
    private FragmentManager fragmentManager;

    public FragmentBasedAppNavigator(FragmentActivity activity, int fragmentContentId) {
        this.activity = activity;
        this.fragmentContentId = fragmentContentId;
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    @Override
    public void openListCategory() {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        Fragment fragment = CategoryListFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(fragmentContentId, fragment)
                .commit();
    }

    @Override
    public void openCategoryDetail(CategoryPresentationModel category) {
        Fragment fragment = CategoryDetailFragment.newInstance(category);
        fragmentManager.beginTransaction()
                .replace(fragmentContentId, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openFolder(FolderPresentationModel folder) {
        Fragment fragment = FolderDetailFragment.newInstance(folder);
        fragmentManager.beginTransaction()
                .replace(fragmentContentId, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openDocument(DocumentPresentationModel document) {
        activity.startActivity(DocumentDetailActivity.createLaunchIntent(activity, document));
    }
}
