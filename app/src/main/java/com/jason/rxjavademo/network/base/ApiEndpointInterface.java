package com.jason.rxjavademo.network.base;

import com.jason.rxjavademo.network.domain.BaseEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Chen Haitao on 2016/5/27.
 */
public interface ApiEndpointInterface {

    @GET("top250")
    Call<BaseEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
