package com.vurtex.weituo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vurtex on 2017/7/23.
 */

public class User implements Parcelable {
    private long id;//用户id
    private String username;//用户名称
    private String password;//用户密码
    private String nickname;//用户昵称
    private String avatar;
    private String age;//用户年龄

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.nickname);
        dest.writeString(this.avatar);
        dest.writeString(this.age);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.username = in.readString();
        this.password = in.readString();
        this.nickname = in.readString();
        this.avatar = in.readString();
        this.age = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
