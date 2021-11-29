package com.neppplus.gudocin_android.api

import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ServerAPIInterface {

//    필요 API들을 만들어주세요.
//    이 파일은 여러명이 같이 편집하게 될겁니다.


//    상품목록 받아오기
    @GET("/product")
    fun getRequestProductList() : Call<BasicResponse>


    //    전체 리뷰 목록 가져오기
    @GET("/review")
    fun getRequestReviewList(): Call<BasicResponse>

//    리뷰 등록
    @FormUrlEncoded
    @POST("/review")
    fun postRequestReviewContent(
    @Field("X-Http-Token") xHttpToken: String,
    @Field("product_id ") productId: Int,
    @Field("title") title: String,
    @Field("content") content: String,
    @Field("score") rating: Int,
    @Field("tag_list") tagList: String,
    ) : Call<BasicResponse>

//    리뷰 목록 랭킹순
    @GET("/review/check_list")
    fun getRequestRankingList() : Call<BasicResponse>



}