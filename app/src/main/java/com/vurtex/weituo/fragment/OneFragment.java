package com.vurtex.weituo.fragment;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.unstoppable.submitbuttonview.SubmitButton;
import com.vurtex.weituo.R;
import com.vurtex.weituo.base.ImmersionBaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;


public class OneFragment extends ImmersionBaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    Unbinder unbinder;
    @BindView(R.id.textView)
    TextView tv_welcome;
    @BindView(R.id.btn_help)
    SubmitButton btn_Help;
    @BindView(R.id.tb_one)
    Toolbar mToolbar;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MyTask task;

    // TODO: Customize parameter initialization
    public static OneFragment newInstance(int columnCount) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), mToolbar);
    }

    @Override
    protected void initView() {
        //Toolbar相关------------------
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
        //----------------------------
        // 设置浪漫雅圆字体 字体格式要为ttf
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvantGardeLT.ttf");
        tv_welcome.setTypeface(typeface);
        btn_Help.setOnClickListener(v -> {
            if (task == null || task.isCancelled()) {
                task = new MyTask();
                task.execute();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_one, menu);
    }

    private class MyTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            int i = 0;
            while (i <= 100) {
                if (isCancelled()) {
                    return null;
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                publishProgress(i);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == null) {
                btn_Help.reset();
            }
            btn_Help.doResult(aBoolean);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            btn_Help.setProgress(values[0]);
        }
    }

}
