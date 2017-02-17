package vn.jupiter.tyrademo.documentdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.jupiter.tyrademo.R;
import vn.jupiter.tyrademo.categorydetail.DocumentPresentationModel;

public class DocumentDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DOCUMENT = "extra_document";

    public static Intent createLaunchIntent(Context context, DocumentPresentationModel document) {
        Intent launchIntent = new Intent(context, DocumentDetailActivity.class);
        launchIntent.putExtra(EXTRA_DOCUMENT, document);
        return launchIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_detail);
        DocumentPresentationModel document = ((DocumentPresentationModel) getIntent().getParcelableExtra(EXTRA_DOCUMENT));
        if (document != null) {
            setTitle(document.getName());
        }
    }
}
