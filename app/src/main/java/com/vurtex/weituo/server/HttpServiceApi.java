package com.vurtex.weituo.server;


import com.vurtex.weituo.entity.LoginResult;
import com.vurtex.weituo.entity.ResultInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    /*上传文件*/
    @Multipart
        @POST("file/uploadAvatar")
    Observable<ResultInfo> uploadImage(@Part("username") RequestBody username, @Part("auth_key") RequestBody  auth_key, @Part MultipartBody.Part file);
}
