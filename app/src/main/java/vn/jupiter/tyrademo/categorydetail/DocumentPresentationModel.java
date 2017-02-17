package vn.jupiter.tyrademo.categorydetail;

import android.os.Parcel;
import android.os.Parcelable;

import vn.jupiter.tyrademo.data.FileType;

public class DocumentPresentationModel implements FileType, Parcelable {

    private String documentName;

    public DocumentPresentationModel(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public String getName() {
        return documentName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.documentName);
    }

    protected DocumentPresentationModel(Parcel in) {
        this.documentName = in.readString();
    }

    public static final Parcelable.Creator<DocumentPresentationModel> CREATOR
            = new Parcelable.Creator<DocumentPresentationModel>() {
        @Override
        public DocumentPresentationModel createFromParcel(Parcel source) {
            return new DocumentPresentationModel(source);
        }

        @Override
        public DocumentPresentationModel[] newArray(int size) {
            return new DocumentPresentationModel[size];
        }
    };
}
