package com.vurtex.weituo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vurtex.weituo.entity.OneListModel;
import com.vurtex.weituo.listener.DebouncedOnClickListener;
import com.vurtex.weituo.viewholder.EasyViewHolder;
import com.vurtex.weituo.viewholder.OneListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: BaseEasyAdapter </br>
 * Description: 有关recyclerView最基本的adapter </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 11/11/2016 2:06 PM</br>
 * Update: 11/11/2016 2:06 PM </br>
 **/

public class OneListAdapter extends RecyclerView.Adapter<OneListViewHolder> {
    private Context mContext;

    //  private EasyViewHolder.OnItemLongClickListener mOnItemLongClickListener;
//  private EasyViewHolder.OnItemLeftScrollListener mOnItemLeftScrollListener;
    private List<OneListModel> mDatas = new ArrayList<>();

    public OneListAdapter(Context context, ArrayList<OneListModel> datas) {
        mContext = context;
        mDatas=datas;
    }
    private EasyViewHolder.OnItemClickListener mOnItemClickListener;

    private void bindListeners(OneListViewHolder easyViewHolder) {
        if (easyViewHolder != null) {
            easyViewHolder.setOnItemClickListener(mOnItemClickListener);
//      easyViewHolder.setOnItemLongClickListener(mOnItemLongClickListener);
//      easyViewHolder.setOnItemLeftScrollListener(mOnItemLeftScrollListener);
        }
    }

    @Override
    public OneListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OneListViewHolder easyViewHolder = new OneListViewHolder(mContext, parent);
        bindListeners(easyViewHolder);
        return easyViewHolder;
    }

    @Override
    public void onBindViewHolder(OneListViewHolder holder, int position) {
        holder.bindTo(position, mDatas.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public void add(OneListModel object, int position) {
        mDatas.add(position, object);
        notifyItemInserted(position);
    }

    public void add(OneListModel object) {
        mDatas.add(object);
        notifyItemInserted(getIndex(object));
    }

    public void addAll(List<OneListModel> objects) {
        mDatas.clear();
        mDatas.addAll(objects);
        notifyDataSetChanged();
    }

    public void appendAll(List<OneListModel> objects) {
        if (objects == null) {
            throw new IllegalArgumentException("objects can not be null");
        }
        List<OneListModel> toAppends = filter(objects);
        final int toAppendSize = toAppends.size();
        if (toAppendSize <= 0) {
            return;
        }
        int prevSize = this.mDatas.size();
        List<OneListModel> data = new ArrayList<>(prevSize + toAppendSize);
        data.addAll(mDatas);
        data.addAll(toAppends);
        mDatas = data;
        notifyItemRangeInserted(prevSize, toAppends.size());
    }

    /**
     * 去掉重复数据
     */
    private List<OneListModel> filter(List<OneListModel> data) {
        List<OneListModel> returnData = new ArrayList<>();
        List<OneListModel> localDataList = this.mDatas;
        for (OneListModel o : data) {
            if (!localDataList.contains(o)) {
                returnData.add(o);
            }
        }
        return returnData;
    }

    public boolean update(OneListModel data, int position) {
        OneListModel oldData = mDatas.set(position, data);
        if (oldData != null) {
            notifyItemChanged(position);
        }
        return oldData != null;
    }

    public boolean remove(OneListModel data) {
        return mDatas.contains(data) && remove(getIndex(data));
    }

    public boolean remove(int position) {
        boolean validIndex = isValidIndex(position);
        if (validIndex) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
        return validIndex;
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public List<OneListModel> getItems() {
        return mDatas;
    }

    public Object get(int position) {
        return mDatas.get(position);
    }

    public int getIndex(Object item) {
        return mDatas.indexOf(item);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void setOnClickListener(final EasyViewHolder.OnItemClickListener listener) {
        this.mOnItemClickListener = new DebouncedOnClickListener() {
            @Override
            public boolean onDebouncedClick(View v, int position) {
                if (listener != null) {
                    listener.onItemClick(position, v);
                }
                return true;
            }
        };
    }

//  public void setOnLongClickListener(final EasyViewHolder.OnItemLongClickListener listener) {
//    this.mOnItemLongClickListener = new DebouncedOnLongClickListener() {
//      @Override public boolean onDebouncedClick(View v, int position) {
//        if (listener!=null){
//          listener.onItemLongClick(position,v);
//        }
//        return true;
//      }
//    };
//  }
//
//  public void setOnLeftClickListener(final EasyViewHolder.OnItemLeftScrollListener onLeftScrollListener){
//    mOnItemLeftScrollListener = new DebouncedOnLeftClickListener() {
//      @Override public boolean onDebouncedClick(View v, int position) {
//        if (onLeftScrollListener!=null){
//          onLeftScrollListener.onItemLeftClick(position,v);
//        }
//        return true;
//      }
//    };
//  }

    private boolean isValidIndex(int position) {
        return position >= 0 && position < getItemCount();
    }
}
