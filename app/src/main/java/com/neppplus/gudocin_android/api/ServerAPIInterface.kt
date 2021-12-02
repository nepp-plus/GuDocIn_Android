package com.neppplus.gudocin_android.api

import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

//    리뷰 등록
    @FormUrlEncoded
    @POST("/review")
    fun postRequestReviewContent(
    @Field("product_id") productId: Int,
    @Field("title") title: String,
    @Field("content") content: String,
    @Field("score") rating: Double,
    @Field("tag_list") tagList: String,
    ) : Call<BasicResponse>

//    리뷰 목록 랭킹순
    @GET("/review/ranking")
    fun getRequestRankingList() : Call<BasicResponse>

    //    모든 카테고리 조회
    @GET("/category")
    fun getRequestCategory(): Call<BasicResponse>

    //    특정 대분류 카테고리(내부의 소분류) 조회
    @GET("/category/{large_category_id}")
    fun getRequestSmallCategoryDependOnLarge(
        @Path("large_category_id") id:Int,
    ): Call<BasicResponse>

    //    모든 소분류 카테고리 조회
    @GET("/category/small")
    fun getRequestCategorySmall(): Call<BasicResponse>

    //   특정 소분류 카테고리(내부의 상품목록) 조회
    @GET("/category/small/{small_category_id}")
    fun getRequestSmallCategorysItemList(
        @Path("small_category_id") id:Int,
    ): Call<BasicResponse>


    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestUpdateUserInfo(
        @Field("field") field: String,
        @Field("value") value: String,
    ): Call<BasicResponse>

    //    배너 가져오기
    @GET("/main/banner")
    fun getRequestMainBanner(): Call<BasicResponse>

//      특정 리뷰 상세보기
    @GET("/review/{review_id}")
    fun getRequestReviewDetail(
    @Path("review_id") reviewId : Int,
    ): Call<BasicResponse>

    //      특정 상품 상세보기
    @GET("/product/{product_id}")
    fun getRequestProductDetail(
        @Path("product_id") reviewId : Int,
    ): Call<BasicResponse>



//    특정 리뷰의 댓글 모아보기
    @GET("review/{review_id}/reply")
    fun getRequestReviewReply(
    @Path("review_id") reveiwReply : Int,
    ): Call<BasicResponse>

//    리뷰에 댓글 작성
    @POST("review/{review_id}/reply")
    fun postRequestReviewReply(
    @Path("review_id") reviewReply: Int,
    @Field("content") content: String,
    ): Call<BasicResponse>



    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditUserInfo(
        @Field("field") field: String,
        @Field("value") value: String,


    ) : Call<BasicResponse>


    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditMyNumber(
        @Field("field") field: String,
        @Field("value") value: String,

    ) : Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditMyPassword(
        @Field("field") field: String,
        @Field("value") value: String,
        @Field("current_password") password: String,


        ) : Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditMyName(
        @Field("field") field: String,
        @Field("value") value: String,

        ) : Call<BasicResponse>




}