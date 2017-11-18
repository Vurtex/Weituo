package com.vurtex.weituo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.vurtex.weituo.R;
import com.vurtex.weituo.adapter.OneListAdapter;
import com.vurtex.weituo.base.ImmersionBaseFragment;
import com.vurtex.weituo.entity.OneListModel;
import com.vurtex.weituo.utils.RecyclerViewUtils;
import com.vurtex.weituo.viewholder.EasyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;


public class TwoFragment extends ImmersionBaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        EasyViewHolder.OnItemClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.swift_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tb_two)
    Toolbar mToolbar;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OneListAdapter mOneListAdapter;

    public ArrayList<OneListModel> arr = new ArrayList<>();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TwoFragment() {
    }

    // TODO: Customize parameter initialization
    public static TwoFragment newInstance(int columnCount) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), mToolbar);
    }

    @Override
    protected void initView() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
        setRefresh();
        // Set the adapter
        setUpAdapter();
    }


    private void setRefresh() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setDistanceToTriggerSync(300);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setUpAdapter() {
        arr.addAll(getOneListModels());
        mOneListAdapter = new OneListAdapter(getActivity(), arr);
        mOneListAdapter.setOnClickListener(this);
        list.setHasFixedSize(true);
        list.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getActivity()));
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(mOneListAdapter);
        mOneListAdapter.notifyDataSetChanged();
    }

    @NonNull
    private ArrayList<OneListModel> getOneListModels() {
        ArrayList<OneListModel> arr = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OneListModel model = new OneListModel();
            model.Name = "item" + i;
            arr.add(model);
        }
        return arr;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> mSwipeRefreshLayout.setRefreshing(false), 3000);
    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
    }

    public void addDateToList() {
        arr.addAll(getOneListModels());
        mOneListAdapter.addAll(arr);
    }

}
