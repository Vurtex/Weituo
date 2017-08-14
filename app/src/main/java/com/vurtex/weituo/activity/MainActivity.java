package com.vurtex.weituo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.vurtex.weituo.R;
import com.vurtex.weituo.base.BaseActivity;
import com.vurtex.weituo.common.HttpManager;
import com.vurtex.weituo.entity.LoginResult;
import com.vurtex.weituo.fragment.FourFragment;
import com.vurtex.weituo.fragment.OneFragment;
import com.vurtex.weituo.server.HttpServiceApi;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
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
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private ArrayList<Fragment> mFragments;
//    private PopupMenu mPopupMenu;

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

    public void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(OneFragment.newInstance(0));
        mFragments.add(OneFragment.newInstance(1));
        mFragments.add(OneFragment.newInstance(2));
        mFragments.add(FourFragment.newInstance(3));
        mFragments.add(OneFragment.newInstance(4));
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
