package com.vurtex.weituo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vurtex on 2017/4/18.
 */

public class RetrofitEntity implements Parcelable {
    public String a;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
    }

    public RetrofitEntity() {
    }

    protected RetrofitEntity(Parcel in) {
        this.a = in.readString();
    }

    public static final Creator<RetrofitEntity> CREATOR = new Creator<RetrofitEntity>() {
        @Override
        public RetrofitEntity createFromParcel(Parcel source) {
            return new RetrofitEntity(source);
        }

        @Override
        public RetrofitEntity[] newArray(int size) {
            return new RetrofitEntity[size];
        }
    };
}
