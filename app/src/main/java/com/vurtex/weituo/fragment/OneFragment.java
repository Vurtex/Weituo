package com.vurtex.weituo.fragment;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unstoppable.submitbuttonview.SubmitButton;
import com.vurtex.weituo.R;
import com.vurtex.weituo.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class OneFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    Unbinder unbinder;
    @BindView(R.id.textView)
    TextView tv_welcome;
    @BindView(R.id.btn_help)
    SubmitButton btn_Help;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MyTask task;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OneFragment() {
    }

    // TODO: Customize parameter initialization
    public static OneFragment newInstance(int columnCount) {
        OneFragment fragment = new OneFragment();
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
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        // 设置浪漫雅圆字体 字体格式要为ttf
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvantGardeLT.ttf");
        tv_welcome.setTypeface(typeface);
        btn_Help.setOnClickListener(v->{
            if (task == null || task.isCancelled()) {
                task = new MyTask();
                task.execute();
            }
        });
        return view;
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
