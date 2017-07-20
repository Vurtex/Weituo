package com.vurtex.weituo.listener;

import android.view.View;

import com.vurtex.weituo.viewholder.EasyViewHolder;


/**
 * Created by kevin on 13/11/2016.
 */

public abstract class DebouncedOnLongClickListener implements DebouncedListener,EasyViewHolder.OnItemLongClickListener {

  private final DebouncedClickHandler debouncedClickHandler;
  protected DebouncedOnLongClickListener() {
    this.debouncedClickHandler = new DebouncedClickHandler(this);
  }

  @Override public boolean onItemLongClick(int position, View view) {
    return debouncedClickHandler.invokeDebouncedClick(position, view);
  }
}
