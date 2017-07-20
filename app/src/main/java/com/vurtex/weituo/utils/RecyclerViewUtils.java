package com.vurtex.weituo.utils;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;

import com.vurtex.weituo.R;


/**
 * Created by kevin on 13/11/2016.
 */

public class RecyclerViewUtils {

  public static RecyclerView.ItemDecoration buildItemDecoration(Context context) {
    DividerItemDecoration decoration = new DividerItemDecoration(context);
    decoration.setInsets(buildInsets());
    final int dividerRes = buildDivider();
    if (dividerRes > 0) {
      decoration.setDivider(dividerRes);
    }
    return decoration;
  }

  public static @DrawableRes
  int buildDivider() {
    return 0;
  }

  public static @DimenRes
  int buildInsets() {
    return R.dimen.divider;
  }
}
