package com.jason.rxjavademo.network.base;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 网络连接帮助类
 * Created by Chen Haitao on 2016/5/27.
 */
public class ConnectionBase {

    static String SERVER_URL = "https://api.douban.com/v2/movie/";

    private static Retrofit retrofit;
    private static Retrofit retrofit2;
    private static ApiEndpointInterface apiService;
    private static ApiEndpointInterfaceObservable apiService2;

    public ConnectionBase() {

    }

    public static void init(Context context) {
        // Define the interceptor, add authentication headers
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
////                Request newRequest = chain.request().newBuilder().addHeader("User-Agent", "Retrofit-Sample-App").build();
//                Request newRequest;
//                Request.Builder builder = chain.request().newBuilder()
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Accept", "application/json");
//
//                //sign
//
//                //JWT token
//
//                newRequest = builder.build();
//
//                return chain.proceed(newRequest);
//            }
//        };
//
//        // Add the interceptor to OkHttpClient
//        OkHttpClient client = new OkHttpClient();
//        client.interceptors().add(interceptor);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        // Set the custom client when building adapter
        retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
                .build();

        // Create endpoint service
        apiService = retrofit.create(ApiEndpointInterface.class);


        // RxJava -- begin
        retrofit2 = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService2 = retrofit2.create(ApiEndpointInterfaceObservable.class);

        // RxJava -- end
    }


    public static ApiEndpointInterface getApiService() {
        if (apiService != null) {
            return apiService;
        } else {
            throw new IllegalStateException("apiService not initialized");
        }
    }

    public static ApiEndpointInterfaceObservable getApiService2() {
        if (apiService2 != null) {
            return apiService2;
        } else {
            throw new IllegalStateException("apiService not initialized");
        }
    }

}
