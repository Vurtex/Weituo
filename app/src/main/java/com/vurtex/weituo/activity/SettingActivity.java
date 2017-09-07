package com.vurtex.weituo.activity;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.vurtex.weituo.R;
import com.vurtex.weituo.base.BaseActivity;

import butterknife.BindView;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn)
    FloatingActionButton btn;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(toolbar).init();
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        btn.setOnClickListener(v -> {
            Snackbar.make(v, "我是Snackbar", Snackbar.LENGTH_LONG)
                    .show();
        });
        imgAvatar.setOnClickListener((v) -> {
            String[] strs = {"从相册选择", "拍照"};
            new AlertDialog.Builder(mContext).setItems(strs, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0)
                        chooseFromGallery();
                    else
                        chooseFromCamera();
                }
            });

        });
    }

    @Override
    protected void setListener() {
        //toolbar返回按钮监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
