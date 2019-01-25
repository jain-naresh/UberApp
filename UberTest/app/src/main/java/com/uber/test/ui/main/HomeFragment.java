package com.uber.test.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.uber.test.R;
import com.uber.test.data.model.FlickrImagePO;
import com.uber.test.data.model.SearchResponsePO;
import com.uber.test.data.service.AsyncTaskListener;
import com.uber.test.data.service.SearchImagesAsyncTask;
import com.uber.test.ui.adapter.FlickerImagesAdapter;
import com.uber.test.ui.base.BaseActivity;
import com.uber.test.ui.base.BaseFragment;
import com.uber.test.utils.AsyncTaskUtil;
import com.uber.test.utils.FragmentUtil;
import com.uber.test.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements AsyncTaskListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final int DEFAULT_PAGE = 1;
    private static final int GRID_SPAN_COUNT = 3;

    private View view;
    private List<FlickrImagePO> photoList = new ArrayList<>();
    private FlickerImagesAdapter searchImagesAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private EditText inputEditText;

    private String searchText = "";
    private String lastSearchText = "";
    private int pageNo = DEFAULT_PAGE;
    private boolean isSearchInProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initComponents();

        return view;
    }

    private void initComponents() {
        RecyclerView recyclerView = view.findViewById(R.id.fragmentHome_rvSearchResults);
        searchImagesAdapter = new FlickerImagesAdapter(
                FragmentUtil.getActivity(this),
                photoList
        );
        recyclerView.setAdapter(searchImagesAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(GRID_SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = staggeredGridLayoutManager.getChildCount();
                    totalItemCount = staggeredGridLayoutManager.getItemCount();
                    int[] firstVisibleItems = null;
                    firstVisibleItems = staggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                    if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                        pastVisibleItems = firstVisibleItems[0];
                    }

                    if (!isSearchInProgress) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            //Do pagination.. i.e. fetch new data
                            fetchImages(searchText, pageNo++);
                        }
                    }
                }
            }
        });
        inputEditText = view.findViewById(R.id.fragmentHome_etSearchText);
        inputEditText.addTextChangedListener(mTextChangeListener);
    }

    private TextWatcher mTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //
            Log.d(TAG, "beforeTextChanged text = " + s.toString());
        }

        @Override
        public void onTextChanged(CharSequence query, int start, int before, int count) {

            Log.d(TAG, "onTextChanged text = " + query.toString());
            searchText = inputEditText.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.d(TAG, "afterTextChanged text = " + editable.toString());
            if (searchText.isEmpty()) {
                lastSearchText = "";
            } else {
                if (!lastSearchText.contentEquals(searchText)) {
                    photoList.clear();
                    searchImagesAdapter.notifyDataSetChanged();
                    pageNo = DEFAULT_PAGE;
                    fetchImages(searchText, pageNo);
                }
                lastSearchText = searchText;
            }
        }
    };

    private void fetchImages(String searchText, int pageNo) {
        if(NetworkUtil.getConnectivityStatus(getActivity())){

            isSearchInProgress = true;
            ((BaseActivity) FragmentUtil.getActivity(this)).showProgressDialog();
            SearchImagesAsyncTask getSearchImagesAsyncTask = new SearchImagesAsyncTask(this, searchText, this.pageNo);
            AsyncTaskUtil.executeAsyncTask(getSearchImagesAsyncTask, true);
        }
        else{
            Toast.makeText(getActivity(),"Please check your network connection",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onTaskCompleted(Object[] params) {
        if (params[0] instanceof SearchResponsePO) {
            isSearchInProgress = false;
            ((BaseActivity) FragmentUtil.getActivity(this)).hideProgressDialog();
            SearchResponsePO searchResponsePO = (SearchResponsePO) params[0];
            if (searchResponsePO != null) {
                photoList.addAll(searchResponsePO.getFlickrPhotoList());
                searchImagesAdapter.notifyDataSetChanged();
                Log.d(TAG, "SearchResponsePO Images = " + searchResponsePO.getFlickrPhotoList().size());
            } else {
                Snackbar snackbar = Snackbar.make(view, getString(R.string.no_response), Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getString(R.string.app_name), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fetchImages(lastSearchText, pageNo);
                    }
                });
            }
        } else {
            super.onTaskCompleted(params);
        }
    }
}
