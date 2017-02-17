package vn.jupiter.tyrademo.categorydetail;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import vn.jupiter.tyrademo.R;
import vn.jupiter.tyrademo.ui.BindableVH;

public class FileBindableVH extends BindableVH<DocumentPresentationModel> {

    @BindView(R.id.tv_file_name)
    TextView tvFileName;

    public FileBindableVH(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(DocumentPresentationModel document) {
        tvFileName.setText(document.getName());
    }
}
