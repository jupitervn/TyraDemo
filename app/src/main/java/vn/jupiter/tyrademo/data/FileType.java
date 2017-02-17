package vn.jupiter.tyrademo.data;

import java.util.Comparator;

public interface FileType {
    String getName();

    class DefaultNameComparator implements Comparator<FileType> {

        @Override
        public int compare(FileType o1, FileType o2) {
            if (o1 != null && o2 != null) {
                return o1.getName().compareTo(o2.getName());
            } else if (o1 != null) {
                return 1;
            } else if (o2 != null) {
                return -1;
            }
            return 0;
        }
    }

}
