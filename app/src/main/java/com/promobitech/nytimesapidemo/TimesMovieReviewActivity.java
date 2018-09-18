package com.promobitech.nytimesapidemo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.promobitech.nytimesapidemo.adapter.MovieAdapter;
import com.promobitech.nytimesapidemo.classe.DatePickerFragment;
import com.promobitech.nytimesapidemo.classe.NYTimes;
import com.promobitech.nytimesapidemo.mvp.IMovieReviewModel;
import com.promobitech.nytimesapidemo.mvp.IMovieReviewPresenter;
import com.promobitech.nytimesapidemo.mvp.IMovieReviewView;
import com.promobitech.nytimesapidemo.mvp.MovieReviewIntractor;
import com.promobitech.nytimesapidemo.mvp.MovieReviewPresenter;
import com.promobitech.nytimesapidemo.room.tables.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimesMovieReviewActivity extends AppCompatActivity implements IMovieReviewView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.txtOpeningDate)
    TextView txtOpeningDate;
    @BindView(R.id.txtPublicationDate)
    TextView txtPublicationDate;
    @BindView(R.id.btnFilter)
    TextView btnFilter;
    @BindView(R.id.etReviewer)
    EditText etReviewer;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final int OPENING_DATE_REQUEST = 1;
    public static final int PUBLICATION_DATE_REQUEST = 2;
    private String publicationDate;
    private String openingDate;
    private String reviewer;
    private KProgressHUD kProgressHUD;
    private IMovieReviewModel iMovieReviewModel;
    private IMovieReviewPresenter iMovieReviewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        kProgressHUD = KProgressHUD.create(this);
        ((NYTimes) getApplication()).getNetComponent().inject(this);
        setSupportActionBar(toolbar);
        swipeContainer.setOnRefreshListener(this);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setUpMvc();
    }

    private void setUpMvc() {
        iMovieReviewModel = new MovieReviewIntractor();
        iMovieReviewPresenter = new
                MovieReviewPresenter(iMovieReviewModel, this, TimesMovieReviewActivity.this);
        //load data with default params (null)
        iMovieReviewPresenter.filterButtonClick(publicationDate, openingDate, reviewer);
    }

    @OnClick(R.id.btnFilter)
    public void filter(View v) {
        reviewer = etReviewer.getText().toString();
        if (TextUtils.isEmpty(reviewer)) {
            //Empty string is invalid value , it should ether string or null
            reviewer = null;
        }
        iMovieReviewPresenter.filterButtonClick(publicationDate, openingDate, reviewer);
    }

    @OnClick(R.id.txtPublicationDate)
    public void showDatePickerDialogPublication(View v) {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("datePicker");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = DatePickerFragment.newInstance(PUBLICATION_DATE_REQUEST);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @OnClick(R.id.txtOpeningDate)
    public void showDatePickerDialogOpeningDate(View v) {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("datePicker");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = DatePickerFragment.newInstance(OPENING_DATE_REQUEST);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setDate(String date, int requestCode) {
        if (requestCode == PUBLICATION_DATE_REQUEST) {
            txtPublicationDate.setText(date);
            publicationDate = date;
        } else {
            txtOpeningDate.setText(date);
            openingDate = date;
        }
    }

    @OnClick(R.id.etPublicationClearImg)
    public void OnClickPublicationClearImg(View v) {
        publicationDate = null;
        txtPublicationDate.setText(null);
    }

    @OnClick(R.id.txtOpeningDateClearImg)
    public void OnClickOpeningDateClearImg(View v) {
        openingDate = null;
        txtOpeningDate.setText(null);
    }

    @OnClick(R.id.etReviewerClearImg)
    public void OnClickReviewerClearImg(View v) {
        reviewer = null;
        etReviewer.setText(null);
    }

    @Override
    public void hideProgress() {
        if (kProgressHUD != null && kProgressHUD.isShowing())
            kProgressHUD.dismiss();
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }
    }

    @Override
    public void showProgress() {
        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.message_processing_request))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    public void showMessages(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Toast.makeText(this, appErrorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getListSuccess(List<Movie> movies) {
        mAdapter = new MovieAdapter(TimesMovieReviewActivity.this, movies);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        iMovieReviewPresenter.filterButtonClick(publicationDate, openingDate, reviewer);
    }
}
