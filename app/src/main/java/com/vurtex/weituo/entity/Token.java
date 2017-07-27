package com.vurtex.weituo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vurtex on 2017/7/26.
 */

public class Token implements Parcelable {
    public String token;
    public String username;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.username);
    }

    public Token() {
    }

    protected Token(Parcel in) {
        this.token = in.readString();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel source) {
            return new Token(source);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
