package vn.jupiter.tyrademo.home;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import vn.jupiter.tyrademo.ListViewWithSearch;
import vn.jupiter.tyrademo.ListViewWithSearchPresenter;
import vn.jupiter.tyrademo.R;
import vn.jupiter.tyrademo.data.DataSchema;

public class CategoryListPresenter implements ListViewWithSearchPresenter {

    private static List<CategoryPresentationModel> sCategory = new ArrayList<>(3);

    static {
        sCategory.add(new CategoryPresentationModel("My/Shared", 0, R.drawable.ic_my_documents));
        sCategory.add(new CategoryPresentationModel("Teacher documents", 1, R.drawable.ic_teachers_documents));
        sCategory.add(new CategoryPresentationModel("Public documents", 2, R.drawable.ic_group_documents));
    }

    private List<CategoryPresentationModel> data = new ArrayList<>();
    private ListViewWithSearch<CategoryPresentationModel> view;

    public CategoryListPresenter(ListViewWithSearch<CategoryPresentationModel> view) {
        this.view = view;
    }

    @Override
    public void searchWithString(String keyword) {
        view.showLoading();
        List<CategoryPresentationModel> displayList = new ArrayList<>(sCategory);
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.toLowerCase();
            for (CategoryPresentationModel categoryPresentationModel : sCategory) {
                if (!categoryPresentationModel.getName().toLowerCase().contains(keyword)) {
                    displayList.remove(categoryPresentationModel);
                }
            }
        }
        List<Task<CategoryPresentationModel>> findCategoryCountTask = new ArrayList<>();
        for (final CategoryPresentationModel category : displayList) {
            if (category.getDocumentCount() == CategoryPresentationModel.UNDEFINED) {
                findCategoryCountTask.add(findCategoryDocCountAsync(category));
            }
        }
        if (!findCategoryCountTask.isEmpty()) {
            Task.whenAllResult(findCategoryCountTask).onSuccess(
                    new Continuation<List<CategoryPresentationModel>, Void>() {
                        @Override
                        public Void then(Task<List<CategoryPresentationModel>> task) throws Exception {
                            view.hideLoading();
                            if (task.isCompleted()) {
                                data = task.getResult();
                                if (data == null) {
                                    data = new ArrayList<>();
                                }
                                view.displayData(data);
                            } else {
                                data.clear();
                                view.displayError(task.getError());
                            }
                            return null;
                        }
                    }, Task.UI_THREAD_EXECUTOR).getResult();
        } else {
            data = displayList;
            view.hideLoading();
            view.displayData(data);
        }

    }

    private Task<CategoryPresentationModel> findCategoryDocCountAsync(final CategoryPresentationModel category) {
        ParseQuery<ParseObject> documentCountQuery = ParseQuery.getQuery(DataSchema.DOCUMENT.TABLE);
        documentCountQuery.whereEqualTo(DataSchema.DOCUMENT.ATTR_TYPE, category.getType());
        return documentCountQuery.countInBackground().continueWith(
                new Continuation<Integer, CategoryPresentationModel>() {
                    @Override
                    public CategoryPresentationModel then(Task<Integer> task) throws Exception {
                        if (task.isCompleted()) {
                            int count = 0;
                            if (task.getResult() != null) {
                                count = task.getResult();
                            }
                            category.setDocumentCount(count);
                        } else if (task.isFaulted()) {
                            throw task.getError();
                        }
                        return category;
                    }
                });
    }
}
