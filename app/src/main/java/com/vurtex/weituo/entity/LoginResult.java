package com.vurtex.weituo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vurtex on 2017/7/23.
 */

public class LoginResult implements Parcelable {
    private ResultInfo resultInfo;
    private User user;
    private Token token;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.resultInfo, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.token, flags);
    }

    public LoginResult() {
    }

    protected LoginResult(Parcel in) {
        this.resultInfo = in.readParcelable(ResultInfo.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.token = in.readParcelable(Token.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginResult> CREATOR = new Parcelable.Creator<LoginResult>() {
        @Override
        public LoginResult createFromParcel(Parcel source) {
            return new LoginResult(source);
        }

        @Override
        public LoginResult[] newArray(int size) {
            return new LoginResult[size];
        }
    };
}
