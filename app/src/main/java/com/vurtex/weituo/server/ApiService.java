package com.vurtex.weituo.server;

import com.vurtex.weituo.common.HttpManager;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Class: ApiService </br>
 * Description: 服务器请求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 29/12/2016 9:50 AM</br>
 * Update: 29/12/2016 9:50 AM </br>
 **/
public class ApiService {

  private volatile static ApiService INSTANCE;

  private OkHttpClient sOkHttpClient;
  private Retrofit sRetrofit;

  private ApiService(String baseUrl, Interceptor... interceptors) {

    sRetrofit = getRetrofit(baseUrl, interceptors);
  }

  public static ApiService getInstance() {
    if (INSTANCE == null) {
      synchronized (ApiService.class) {
        if (INSTANCE == null) {
          INSTANCE = new ApiService(HttpManager.BASE_URL,new HttpLoggingInterceptor());
        }
      }
    }
    return INSTANCE;
  }

  private OkHttpClient getOkHttpClient(Interceptor... interceptors) {
    if (sOkHttpClient == null) {
      synchronized (this) {
        if (sOkHttpClient == null) {
          OkHttpClient.Builder builder = new OkHttpClient.Builder();
          //TODO 原先为10 7.31日 Vurtex修改
          builder.connectTimeout(0, TimeUnit.SECONDS);
          builder.addInterceptor(new HttpLoggingInterceptor());
          /*for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
          }*/
          HttpLoggingInterceptor httpLoggingInterceptor =
              new HttpLoggingInterceptor();
          httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
          builder.addInterceptor(httpLoggingInterceptor);
          builder.retryOnConnectionFailure(true);
          sOkHttpClient = builder.build();
        }
      }
    }
    return sOkHttpClient;
  }

  private Retrofit getRetrofit(String baseUrl, Interceptor... interceptors) {
    if (sRetrofit == null) {
      synchronized (this) {
        if (sRetrofit == null) {
          sRetrofit = new Retrofit.Builder().client(getOkHttpClient(interceptors))
              .addConverterFactory(JacksonConverterFactory.create())
              .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
              .baseUrl(baseUrl)
              .build();
        }
      }
    }
    return sRetrofit;
  }

  public <T> T createApiService(Class<T> apiService) {
    return sRetrofit.create(apiService);
  }
}
