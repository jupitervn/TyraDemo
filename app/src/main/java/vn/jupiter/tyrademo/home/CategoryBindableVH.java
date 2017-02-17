package vn.jupiter.tyrademo.home;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import vn.jupiter.tyrademo.R;
import vn.jupiter.tyrademo.ui.BindableVH;

public class CategoryBindableVH extends BindableVH<CategoryPresentationModel> {

    private final Resources resources;
    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;
    @BindView(R.id.tv_category_document_count)
    TextView tvCategoryDocumentCount;
    @BindView(R.id.iv_category_icon)
    ImageView ivCategoryIcon;

    public CategoryBindableVH(View itemView) {
        super(itemView);
        resources = itemView.getContext().getResources();
    }

    @Override
    public void bind(CategoryPresentationModel categoryPresentationModel) {
        ivCategoryIcon.setImageResource(categoryPresentationModel.getFolderIconResource());
        tvCategoryName.setText(categoryPresentationModel.getName());
        tvCategoryDocumentCount.setText(resources.getQuantityString(R.plurals.document_count, categoryPresentationModel.getDocumentCount(), categoryPresentationModel.getDocumentCount()));
    }
}
