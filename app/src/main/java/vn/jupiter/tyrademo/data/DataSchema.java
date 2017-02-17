package vn.jupiter.tyrademo.data;

public interface DataSchema {
    interface FOLDER {
        String TABLE = "temp_folder";
        String ATTR_NAME = "name";
        String ATTR_TYPE = "type";
        String ATTR_ID = "objectId";
    }

    interface DOCUMENT {
        String TABLE = "temp_document";
        String ATTR_NAME = "temp_name";
        String ATTR_TYPE = "type";
        String ATTR_FOLDER = "folder";
    }

}
