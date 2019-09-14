package com.example.RetrofitRxJavaDemo.http;

import com.example.RetrofitRxJavaDemo.Result.TestResult;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Retrofit网络接口，接口返回的是一个Observable
 */
public interface HttpAPI {

    @GET("singlePoetry")
    Observable<Result<String>> getData1();

    //示例写法GET
    @GET("singlePoetry")
    Observable<Result<String>> getData2(
            @Query("id") int id
    );

    //示例写法POST
    @FormUrlEncoded
    @POST("singlePoetry")
    Observable<Result<TestResult>> changeData1(
            @Field("id") int id,
            @Field("page") int page
    );

    //示例写法PUT
    @FormUrlEncoded
    @PUT("singlePoetry")
    Observable<Result<String>> changeData2(
            @Field("id") int id,
            @Field("page") int page
    );

    //示例写法DELETE
    @DELETE("singlePoetry")
    Observable<Result<String>> logout(
            @Query("id") int id
    );
}
