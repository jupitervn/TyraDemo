package vn.jupiter.tyrademo.home;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryPresentationModel implements Parcelable {
    public static final int UNDEFINED = -1;

    private String name;
    private int type;
    private int documentCount = UNDEFINED;
    private int folderIconResource;

    public CategoryPresentationModel(String name, int type, int folderIconResource) {
        this.name = name;
        this.type = type;
        this.folderIconResource = folderIconResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(int documentCount) {
        this.documentCount = documentCount;
    }

    public int getFolderIconResource() {
        return folderIconResource;
    }

    public void setFolderIconResource(int folderIconResource) {
        this.folderIconResource = folderIconResource;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.type);
        dest.writeInt(this.documentCount);
        dest.writeInt(this.folderIconResource);
    }

    protected CategoryPresentationModel(Parcel in) {
        this.name = in.readString();
        this.type = in.readInt();
        this.documentCount = in.readInt();
        this.folderIconResource = in.readInt();
    }

    public static final Parcelable.Creator<CategoryPresentationModel> CREATOR
            = new Parcelable.Creator<CategoryPresentationModel>() {
        @Override
        public CategoryPresentationModel createFromParcel(Parcel source) {
            return new CategoryPresentationModel(source);
        }

        @Override
        public CategoryPresentationModel[] newArray(int size) {
            return new CategoryPresentationModel[size];
        }
    };
}
