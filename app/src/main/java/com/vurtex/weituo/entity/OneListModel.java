package com.vurtex.weituo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: GroupData </br>
 * Description: 构建的群组数据 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 14/01/2017 8:28 PM</br>
 * Update: 14/01/2017 8:28 PM </br>
 **/

public class OneListModel implements Parcelable {
  public String Id;
  public String Name;
  public String Avatar;


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.Id);
    dest.writeString(this.Name);
    dest.writeString(this.Avatar);
  }

  public OneListModel() {
  }

  protected OneListModel(Parcel in) {
    this.Id = in.readString();
    this.Name = in.readString();
    this.Avatar = in.readString();
  }

  public static final Parcelable.Creator<OneListModel> CREATOR = new Parcelable.Creator<OneListModel>() {
    @Override
    public OneListModel createFromParcel(Parcel source) {
      return new OneListModel(source);
    }

    @Override
    public OneListModel[] newArray(int size) {
      return new OneListModel[size];
    }
  };
}
