package com.jason.rxjavademo.network.base;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 网络连接帮助类
 * Created by Chen Haitao on 2016/5/27.
 */
public class ConnectionBase {

    static String SERVER_URL = "https://api.douban.com/v2/movie/";

    private static Retrofit retrofit;
    private static ApiEndpointInterface apiService;

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
//
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        // Set the custom client when building adapter
        retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
                .build();

        // Create endpoint service
        apiService = retrofit.create(ApiEndpointInterface.class);
    }


    public static ApiEndpointInterface getApiService() {
        if (apiService != null) {
            return apiService;
        } else {
            throw new IllegalStateException("apiService not initialized");
        }
    }
}
