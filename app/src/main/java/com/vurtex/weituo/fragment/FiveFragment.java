package com.vurtex.weituo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vurtex.weituo.R;
import com.vurtex.weituo.adapter.OneListAdapter;
import com.vurtex.weituo.entity.OneListModel;
import com.vurtex.weituo.utils.RecyclerViewUtils;
import com.vurtex.weituo.viewholder.EasyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FiveFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        EasyViewHolder.OnItemClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.swift_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    Unbinder unbinder;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OneListAdapter mOneListAdapter;

    public ArrayList<OneListModel> arr=new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FiveFragment() {
    }

    // TODO: Customize parameter initialization
    public static FiveFragment newInstance(int columnCount) {
        FiveFragment fragment = new FiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRefresh();
        // Set the adapter
        setUpAdapter();
        return view;
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
        mOneListAdapter = new OneListAdapter(getActivity(),arr);
        mOneListAdapter.setOnClickListener(this);
        list.setHasFixedSize(true);
        list.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getActivity()));
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(mOneListAdapter);
        mOneListAdapter.notifyDataSetChanged();
    }

    @NonNull
    private ArrayList<OneListModel> getOneListModels() {
        ArrayList<OneListModel> arr =new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OneListModel model = new OneListModel();
            model.Name="item"+i;
            arr.add(model);
        }
        return arr;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> mSwipeRefreshLayout.setRefreshing(false), 3000);
    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
    }

    public void addDateToList() {
        arr.addAll(getOneListModels());
        mOneListAdapter.addAll(arr);
    }
}
