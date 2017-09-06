package com.vurtex.weituo.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.gyf.barlibrary.ImmersionBar;
import com.vurtex.weituo.R;
import com.vurtex.weituo.base.ImmersionBaseFragment;
import com.vurtex.weituo.entity.OneListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;


public class FourFragment extends ImmersionBaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tb_four)
    Toolbar mToolbar;
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

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_four;
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
        // Set the adapter
        setUpAdapter();

        imgAvatar.setOnClickListener((v) -> {
            String[] strs = {"从相册选择", "拍照"};
            new AlertDialog.Builder(getActivity()).setItems(strs, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    if (which == 0)
//                        chooseFromGallery();
//                    else
//                        chooseFromCamera();
                }
            }).show();
        });
    }


    private void setUpAdapter() {
        String[] array = {"设置", "wlan", "移动网络", "声音", "显示", "存储", "电池", "设置", "wlan", "移动网络", "声音", "显示", "存储", "电池", "设置", "wlan", "移动网络", "声音", "显示", "存储", "电池", "引用程序"};
        list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, array));
    }

}
