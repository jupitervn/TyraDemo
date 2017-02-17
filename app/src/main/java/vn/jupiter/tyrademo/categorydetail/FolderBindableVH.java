package vn.jupiter.tyrademo.categorydetail;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import vn.jupiter.tyrademo.R;
import vn.jupiter.tyrademo.ui.BindableVH;

public class FolderBindableVH extends BindableVH<FolderPresentationModel> {

    private final Resources resources;
    @BindView(R.id.tv_folder_name)
    TextView tvFolderName;
    @BindView(R.id.tv_folder_document_count)
    TextView tvFolderDocumentCount;

    public FolderBindableVH(View itemView) {
        super(itemView);
        resources = itemView.getContext().getResources();
    }

    @Override
    public void bind(FolderPresentationModel folder) {
        tvFolderName.setText(folder.getName());
        tvFolderDocumentCount.setText(resources.getQuantityString(R.plurals.document_count, folder.getFolderFileCount(), folder.getFolderFileCount()));
    }
}
