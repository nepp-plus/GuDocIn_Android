package com.neppplus.gudocin_android.api

import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.http.*

interface ServerAPIInterface {

//    필요 API들을 만들어주세요.
//    이 파일은 여러명이 같이 편집하게 될겁니다.

//    로그인 기능 명세
    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") pw: String,
    ): Call<BasicResponse>

//    회원가입 기능

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nick: String,
    ): Call<BasicResponse>

//    중복 확인 기능  - GET
    @GET("/user/check")
    fun getRequestDuplicatedCheck(
        @Query("type") type: String,
        @Query("value") value: String,
    ): Call<BasicResponse>

//    소셜로그인 기능 - POST

    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(
        @Field("provider") provider: String,
        @Field("uid") uid: String,
        @Field("nick_name") name: String,
    ): Call<BasicResponse>

//    내 정보 조회 - GET / 토큰값 (임시방안)

    @GET("/user")
    fun getRequestMyInfo(): Call<BasicResponse>

//    상품목록 받아오기
    @GET("/product")
    fun getRequestProductList(): Call<BasicResponse>

//    전체 리뷰 목록 가져오기
    @GET("/review")
    fun getRequestReviewList(): Call<BasicResponse>

}