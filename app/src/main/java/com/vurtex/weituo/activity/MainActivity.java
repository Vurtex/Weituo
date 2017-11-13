package com.vurtex.weituo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.vurtex.weituo.R;
import com.vurtex.weituo.base.ImmersionBaseActivity;
import com.vurtex.weituo.common.HttpManager;
import com.vurtex.weituo.entity.LoginResult;
import com.vurtex.weituo.fragment.FiveFragment;
import com.vurtex.weituo.fragment.FourFragment;
import com.vurtex.weituo.fragment.OneFragment;
import com.vurtex.weituo.fragment.ThreeFragment;
import com.vurtex.weituo.fragment.TwoFragment;
import com.vurtex.weituo.server.HttpServiceApi;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import work.wanghao.simplehud.SimpleHUD;

import static work.wanghao.simplehud.SimpleHUD.showErrorMessage;


public class MainActivity extends ImmersionBaseActivity {
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.tv_toolbar)
    TextView tv_top_title;
    @BindView(R.id.image)
    ImageView img_top_btn;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ArrayList<Fragment> mFragments;

    //    private PopupMenu mPopupMenu;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存BottomBar的状态
        mBottomBar.onSaveInstanceState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    public void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(OneFragment.newInstance(0));
        mFragments.add(TwoFragment.newInstance(1));
        mFragments.add(ThreeFragment.newInstance(2));
        mFragments.add(FourFragment.newInstance(3));
        mFragments.add(FiveFragment.newInstance(4));
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(mToolbar)
                .navigationBarColor(R.color.colorBackground)
                .init();
    }


    @Override
    protected void initView() {
        super.initView();
        tv_top_title.setText("Employ");
        img_top_btn.setVisibility(View.VISIBLE);
        img_top_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });
        initFragments();
        tabListen();
        updateCheck();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    private void updateCheck() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpManager.BASE_URL)
                .build();

        HttpServiceApi apiService = retrofit.create(HttpServiceApi.class);
        Observable<LoginResult> observable = apiService.dologin("", "");
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<LoginResult>() {
                            @Override
                            public void onCompleted() {
                                SimpleHUD.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                SimpleHUD.dismiss();
                            }

                            @Override
                            public void onNext(LoginResult loginResult) {
//                                    tvMsg.setText("无封装：\n" + retrofitEntity.a.toString());
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                showErrorMessage(MainActivity.this, "请稍后.");
                            }
                        });
    }


    public void naviFragment(Fragment fragment) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.contentContainer, fragment);
        mTransaction.commit();
    }

    public void tabListen() {
        mBottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.tab_one:
                    naviFragment(mFragments.get(0));
                    break;
                case R.id.tab_two:
                    naviFragment(mFragments.get(1));
                    break;
                case R.id.tab_three:
                    naviFragment(mFragments.get(2));
                    break;
                case R.id.tab_four:
                    naviFragment(mFragments.get(3));
                    break;
                case R.id.tab_five:
                    naviFragment(mFragments.get(4));
                    break;

            }
        });
    }

}
