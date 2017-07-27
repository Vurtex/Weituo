package com.vurtex.weituo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.vurtex.weituo.R;
import com.vurtex.weituo.base.BaseActivity;
import com.vurtex.weituo.common.HttpManager;
import com.vurtex.weituo.entity.LoginResult;
import com.vurtex.weituo.fragment.OneFragment;
import com.vurtex.weituo.server.HttpServiceApi;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
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


public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar)
    TextView mTvToolbar;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private ArrayList<Fragment> mFragments;
    private PopupMenu mPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        init();
        SimpleHUD.showErrorMessage(this, "", () -> {

        });
    }

    private void init() {
        initToolbar();
        initFragments();
        tabListen();
        updateCheck();
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

    public void initToolbar() {
        mToolbar.setTitle("");
        mImage.setVisibility(View.VISIBLE);
        mTvToolbar.setText("委托");
        setSupportActionBar(mToolbar);
//        mImage.setOnClickListener(v -> );
    }

    public void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(OneFragment.newInstance(0));
        mFragments.add(OneFragment.newInstance(1));
        mFragments.add(OneFragment.newInstance(2));
        mFragments.add(OneFragment.newInstance(3));
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

            }
        });
    }

    @OnClick(R.id.image)
    public void onClick(View v) {
        showPopupMenu();
    }

    public void showPopupMenu() {
        mPopupMenu = new PopupMenu(this, mImage);
        mPopupMenu.getMenuInflater().inflate(R.menu.menu_popup, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_one:
                    OneFragment fragment = (OneFragment) mFragments.get(0);
                    fragment.addDateToList();
                    Toast.makeText(MainActivity.this, "itemId" + item.getItemId(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.create_group:
                    Toast.makeText(MainActivity.this, "itemId" + item.getItemId(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.search_group:
                    Toast.makeText(MainActivity.this, "itemId" + item.getItemId(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.free_call:
                    Toast.makeText(MainActivity.this, "itemId" + item.getItemId(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.audio_meet:
                    Toast.makeText(MainActivity.this, "itemId" + item.getItemId(), Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });
        mPopupMenu.show();
    }

    @Override
    protected boolean preSetupToolbar() {
        return false;
    }
}
