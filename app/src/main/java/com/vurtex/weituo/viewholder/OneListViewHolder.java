package com.vurtex.weituo.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vurtex.weituo.R;
import com.vurtex.weituo.entity.OneListModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class:  </br>
 * Description: 所有群组ViewHolder </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 14/01/2017 8:26 PM</br>
 * Update: 14/01/2017 8:26 PM </br>
 **/

public class OneListViewHolder extends EasyViewHolder<OneListModel> {

  @BindView(R.id.img_avatar)
  ImageView mGroupAvatar;
  @BindView(R.id.tv_name) TextView mGroupName;
  private Context mContext;

  public OneListViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_one);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, OneListModel Data) {
    Glide.with(mContext)
        .load(R.mipmap.ic_launcher)/*groupData.Avatar)*/
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .crossFade()
        .into(mGroupAvatar);
    mGroupName.setText(Data.Name);
  }
}
