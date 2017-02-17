package vn.jupiter.tyrademo.categorydetail;

import vn.jupiter.tyrademo.data.FileType;

public class FolderPresentationModel implements FileType {
    private String folderId;
    private String folderName;
    private int folderFileCount;

    public FolderPresentationModel(String folderId, String folderName, int folderFileCount) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.folderFileCount = folderFileCount;
    }

    @Override
    public String getName() {
        return folderName;
    }

    public int getFolderFileCount() {
        return folderFileCount;
    }

    public void increaseFolderCount() {
        folderFileCount++;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }
}
