package vn.jupiter.tyrademo;

import java.util.List;

public interface ListViewWithSearch<T> {
    void displayData(List<T> data);
    void displayError(Exception ex);
    void showLoading();
    void hideLoading();
}
