package com.vurtex.weituo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vurtex on 2017/7/23.
 */

public class ResultInfo implements Parcelable {
    private int resultCode;
    private String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resultCode);
        dest.writeString(this.resultMsg);
    }

    public ResultInfo() {
    }

    protected ResultInfo(Parcel in) {
        this.resultCode = in.readInt();
        this.resultMsg = in.readString();
    }

    public static final Parcelable.Creator<ResultInfo> CREATOR = new Parcelable.Creator<ResultInfo>() {
        @Override
        public ResultInfo createFromParcel(Parcel source) {
            return new ResultInfo(source);
        }

        @Override
        public ResultInfo[] newArray(int size) {
            return new ResultInfo[size];
        }
    };
}
