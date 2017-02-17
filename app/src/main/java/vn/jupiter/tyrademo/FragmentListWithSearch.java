package vn.jupiter.tyrademo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.Unbinder;
import vn.jupiter.tyrademo.ui.DataAdapter;
import vn.jupiter.tyrademo.ui.SimpleOnItemClickListener;

public abstract class FragmentListWithSearch<T> extends Fragment implements ListViewWithSearch<T>,SimpleOnItemClickListener.OnItemClickListener {

    public static final String ARG_SAVED_KEYWORD = "arg_keyword";
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.et_keyword_input)
    EditText etKeywordInput;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;

    protected DataAdapter<T> adapter;
    protected AppNavigator appNavigator;

    private Unbinder viewBinder;
    private ListViewWithSearchPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_with_search, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appNavigator = new FragmentBasedAppNavigator(getActivity(), R.id.fl_content);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinder = ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvData.setLayoutManager(layoutManager);
        rvData.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        rvData.addOnItemTouchListener(new SimpleOnItemClickListener(rvData, this));
        adapter = createAdapter();
        rvData.setAdapter(adapter);
        presenter = createPresenter();
        String keyword = null;
        if (savedInstanceState != null) {
            keyword = etKeywordInput.getText().toString();
        }
        triggerSearch(keyword);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected abstract DataAdapter<T> createAdapter();

    protected abstract ListViewWithSearchPresenter createPresenter();

    private void triggerSearch(@Nullable String keyword) {
        presenter.searchWithString(keyword);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinder.unbind();
    }

    @OnEditorAction(R.id.et_keyword_input)
    public boolean onSearchAction(EditText etSearch) {
        triggerSearch(etSearch.getText().toString());
        return true;
    }

    @Override
    public void displayData(List<T> data) {
        tvError.setVisibility(View.GONE);
        rvData.setVisibility(View.VISIBLE);
        adapter.setItems(data);
    }

    @Override
    public void displayError(Exception ex) {
        rvData.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbProgress.setVisibility(View.GONE);
    }
}
