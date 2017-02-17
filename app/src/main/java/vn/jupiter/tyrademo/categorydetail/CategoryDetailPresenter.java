package vn.jupiter.tyrademo.categorydetail;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bolts.Continuation;
import bolts.Task;
import vn.jupiter.tyrademo.ListViewWithSearch;
import vn.jupiter.tyrademo.ListViewWithSearchPresenter;
import vn.jupiter.tyrademo.data.DataSchema;
import vn.jupiter.tyrademo.data.FileType;

public class CategoryDetailPresenter implements ListViewWithSearchPresenter {

    private int categoryType = -1;
    private List<FileType> categoryData = new ArrayList<>();
    private ListViewWithSearch<FileType> view;

    public CategoryDetailPresenter(int categoryType, ListViewWithSearch<FileType> view) {
        this.categoryType = categoryType;
        this.view = view;
    }

    @Override
    public void searchWithString(String keyword) {
        view.showLoading();
        List<Task<List<FileType>>> categoryQueryTasks = new ArrayList<>();
        categoryQueryTasks.add(getFolderOfCategory(keyword));

        categoryQueryTasks.add(getIndependentDocumentOfCategory(keyword));

        Task.whenAllResult(categoryQueryTasks).onSuccess(new Continuation<List<List<FileType>>, Void>() {
            @Override
            public Void then(Task<List<List<FileType>>> task) throws Exception {
                view.hideLoading();
                if (task.isCompleted()) {
                    List<List<FileType>> result = task.getResult();
                    List<FileType> displayData = new ArrayList<>();
                    for (List<FileType> searchableModels : result) {
                        displayData.addAll(searchableModels);
                    }
                    categoryData = displayData;
                    view.displayData(categoryData);
                } else if (task.isFaulted()) {
                    categoryData.clear();
                    view.displayError(task.getError());
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

    }

    private Task<List<FileType>> getIndependentDocumentOfCategory(String keyword) {
        ParseQuery<ParseObject> documentOfCategoryQuery = ParseQuery.getQuery(DataSchema.DOCUMENT.TABLE);
        documentOfCategoryQuery.whereEqualTo(DataSchema.DOCUMENT.ATTR_TYPE, categoryType)
                                .whereDoesNotExist(DataSchema.DOCUMENT.ATTR_FOLDER)
                                .addAscendingOrder(DataSchema.DOCUMENT.ATTR_NAME);
        if (keyword != null && !keyword.trim().isEmpty()) {
            documentOfCategoryQuery.whereMatches(DataSchema.DOCUMENT.ATTR_NAME, "(" + keyword + ")", "i");
        }

        return documentOfCategoryQuery.findInBackground().continueWith(
                new Continuation<List<ParseObject>, List<FileType>>() {
                    @Override
                    public List<FileType> then(Task<List<ParseObject>> task) throws Exception {
                        if (task.isCompleted()) {
                            List<ParseObject> result = task.getResult();
                            List<FileType> fileList = new ArrayList<>();
                            if (result != null) {
                                for (ParseObject parseObject : result) {
                                    fileList.add(new DocumentPresentationModel(parseObject.getString(DataSchema.DOCUMENT.ATTR_NAME)));
                                }
                            }
                            return fileList;
                        } else if (task.isFaulted()) {
                            throw task.getError();
                        }
                        return new ArrayList<>();
                    }
                });
    }

    private Task<List<FileType>> getFolderOfCategory(String keyword) {
        ParseQuery<ParseObject> folderOfCategoryQuery = ParseQuery.getQuery(DataSchema.DOCUMENT.TABLE);
        folderOfCategoryQuery.whereEqualTo(DataSchema.DOCUMENT.ATTR_TYPE, categoryType)
                .whereExists(DataSchema.DOCUMENT.ATTR_FOLDER)
                .include(DataSchema.DOCUMENT.ATTR_FOLDER);
        if (keyword != null && !keyword.trim().isEmpty()) {
            folderOfCategoryQuery.whereMatchesQuery(DataSchema.DOCUMENT.ATTR_FOLDER,
                    ParseQuery.getQuery(DataSchema.FOLDER.TABLE)
                            .whereMatches(DataSchema.FOLDER.ATTR_NAME, "(" + keyword + ")", "i"));
        }
        return folderOfCategoryQuery.findInBackground()
                .continueWith(new Continuation<List<ParseObject>, List<FileType>>() {
                    @Override
                    public List<FileType> then(Task<List<ParseObject>> task) throws Exception {
                        if (task.isCompleted()) {
                            List<ParseObject> result = task.getResult();
                            Map<String, FolderPresentationModel> folderMap = new LinkedHashMap<>();
                            if (result != null) {
                                for (ParseObject parseObject : result) {
                                    ParseObject folderParseObject = parseObject
                                            .getParseObject(DataSchema.DOCUMENT.ATTR_FOLDER);
                                    String objectId = folderParseObject.getObjectId();
                                    if (folderMap.containsKey(objectId)) {
                                        folderMap.get(objectId).increaseFolderCount();
                                    } else {
                                        FolderPresentationModel folder = new FolderPresentationModel(objectId,
                                                folderParseObject.getString(DataSchema.FOLDER.ATTR_NAME), 1);
                                        folderMap.put(objectId, folder);
                                    }
                                }
                                ArrayList<FileType> folderList = new ArrayList<FileType>(folderMap.values());
                                Collections.sort(folderList, new FileType.DefaultNameComparator());
                                return folderList;
                            }
                        } else if (task.isFaulted()) {
                            throw task.getError();
                        }
                        return new ArrayList<>();
                    }
                });
    }
}
