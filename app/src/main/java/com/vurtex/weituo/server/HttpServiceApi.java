package com.vurtex.weituo.server;


import com.vurtex.weituo.entity.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Vurtex on 2017/4/18.
 */

public interface HttpServiceApi {
    @FormUrlEncoded
    @POST("user/doLogin")
    Observable<LoginResult> dologin(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("user/registerUser")
    Observable<LoginResult> doregister(@Field("username") String username, @Field("password") String password, @Field("nickname") String nickname, @Field("age") String age, @Field("avatar") String avatar);
}
