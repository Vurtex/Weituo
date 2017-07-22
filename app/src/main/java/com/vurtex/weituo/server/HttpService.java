package com.vurtex.weituo.server;


import com.vurtex.weituo.entity.RetrofitEntity;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Vurtex on 2017/4/18.
 */

public interface HttpService {
    @POST("AppFiftyToneGraph/videoLink")
    Observable<RetrofitEntity> getAllVedioBy(@Body boolean once_no);
}
