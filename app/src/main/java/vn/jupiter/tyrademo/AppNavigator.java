package vn.jupiter.tyrademo;

import vn.jupiter.tyrademo.categorydetail.DocumentPresentationModel;
import vn.jupiter.tyrademo.categorydetail.FolderPresentationModel;
import vn.jupiter.tyrademo.home.CategoryPresentationModel;

public interface AppNavigator {
    void openListCategory();
    void openCategoryDetail(CategoryPresentationModel category);
    void openFolder(FolderPresentationModel folder);
    void openDocument(DocumentPresentationModel document);
}
