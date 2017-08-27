package com.vurtex.weituo.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.vurtex.weituo.R;
import com.vurtex.weituo.base.BaseFragment;
import com.vurtex.weituo.entity.OneListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import qiu.niorgai.StatusBarCompat;


public class FourFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.img_canvas)
    ImageView imgCanvas;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    Unbinder unbinder;
    public ArrayList<OneListModel> arr = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FourFragment() {
    }

    // TODO: Customize parameter initialization
    public static FourFragment newInstance(int columnCount) {
        FourFragment fragment = new FourFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StatusBarCompat.setStatusBarColorForCollapsingToolbar(getActivity(), mAppBarLayout, mCollapsingToolbarLayout, mToolbar, Color.TRANSPARENT);

        // Set the adapter
        setUpAdapter();

        imgAvatar.setOnClickListener((v)-> {
            String[] strs = {"从相册选择", "拍照"};
            new AlertDialog.Builder(getActivity()).setItems(strs, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0)
                        chooseFromGallery();
                    else
                        chooseFromCamera();
                }
            }).show();
        });
//        SimpleHUD.showLoadingMessage(LoginActivity.this, "正在登录...", true);
//        //TODO 去登录
//        HttpServiceApi mHttpServiceApi = ApiService.getInstance().createApiService(HttpServiceApi.class);
//        mHttpServiceApi.dologin(username, password).observeOn(AndroidSchedulers.mainThread()).subscribeOn(
//                Schedulers.io()).subscribe((loginResult -> {
//            SimpleHUD.dismiss();
//            ResultInfo resultInfo = loginResult.getResultInfo();
//            if (resultInfo.getResultCode() == 1) {
//                PowerApp.user = loginResult.getUser();
//                PowerApp.token = loginResult.getToken();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(LoginActivity.this, resultInfo.getResultMsg(), Toast.LENGTH_SHORT).show();
//            }
//        }), new ErrorAction() {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//                SimpleHUD.showErrorMessage(LoginActivity.this, "" + throwable.getMessage());
//            }
//        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColorForCollapsingToolbar(getActivity(), mAppBarLayout, mCollapsingToolbarLayout, mToolbar, Color.TRANSPARENT);
        }
    }

    private void setUpAdapter() {
        String[] array = {"设置", "wlan", "移动网络", "声音", "显示", "存储", "电池", "设置", "wlan", "移动网络", "声音", "显示", "存储", "电池", "设置", "wlan", "移动网络", "声音", "显示", "存储", "电池", "引用程序"};
        list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, array));
    }
}
