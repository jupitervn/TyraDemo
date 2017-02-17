package vn.jupiter.tyrademo.folderdetail;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import vn.jupiter.tyrademo.ListViewWithSearch;
import vn.jupiter.tyrademo.ListViewWithSearchPresenter;
import vn.jupiter.tyrademo.data.DataSchema;
import vn.jupiter.tyrademo.categorydetail.DocumentPresentationModel;
import vn.jupiter.tyrademo.data.FileType;

public class FolderDetailPresenter implements ListViewWithSearchPresenter {

    private String folderId;
    private ListViewWithSearch<FileType> view;

    public FolderDetailPresenter(String folderId, ListViewWithSearch<FileType> view) {
        this.folderId = folderId;
        this.view = view;
    }

    @Override
    public void searchWithString(String keyword) {
        view.showLoading();
        ParseQuery<ParseObject> documentOfCategoryQuery = ParseQuery.getQuery(DataSchema.DOCUMENT.TABLE);
        documentOfCategoryQuery
                .whereMatchesQuery(DataSchema.DOCUMENT.ATTR_FOLDER, ParseQuery.getQuery(DataSchema.FOLDER.TABLE)
                        .whereEqualTo(DataSchema.FOLDER.ATTR_ID, folderId))
                .addAscendingOrder(DataSchema.DOCUMENT.ATTR_NAME);
        if (keyword != null && !keyword.trim().isEmpty()) {
            documentOfCategoryQuery.whereMatches(DataSchema.DOCUMENT.ATTR_NAME, "(" + keyword + ")", "i");
        }

        documentOfCategoryQuery.findInBackground()
                .continueWith(new Continuation<List<ParseObject>, List<FileType>>() {
                    @Override
                    public List<FileType> then(Task<List<ParseObject>> task) throws Exception {
                        List<FileType> fileList = new ArrayList<>();
                        if (task.isCompleted()) {
                            List<ParseObject> result = task.getResult();
                            if (result != null) {
                                for (ParseObject parseObject : result) {
                                    fileList.add(new DocumentPresentationModel(
                                            parseObject.getString(DataSchema.DOCUMENT.ATTR_NAME)));
                                }
                            }
                        } else if (task.isFaulted()) {
                            throw task.getError();
                        }
                        return fileList;
                    }
                })
                .onSuccess(new Continuation<List<FileType>, Void>() {
                    @Override
                    public Void then(Task<List<FileType>> task) throws Exception {
                        view.hideLoading();
                        if (task.isCompleted()) {
                            List<FileType> result = task.getResult();
                            if (result == null) {
                                result = new ArrayList<>();
                            }
                            view.displayData(result);
                        } else if (task.isFaulted()) {
                            view.displayError(task.getError());
                        }
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
    }
}
