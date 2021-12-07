package com.neppplus.gudocin_android.api

import com.neppplus.gudocin_android.datas.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Field("phone") phone: String,
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

//    리뷰 등록 - 멀티파트로 파일도 같이 첨부
    @Multipart
    @POST("/review")
    fun postRequestReviewContent(
        @PartMap params: HashMap<String, RequestBody>,
        @Part img: MultipartBody.Part

    ): Call<BasicResponse>

//    리뷰 목록 랭킹순
    @GET("/review/ranking")
    fun getRequestRankingList(): Call<BasicResponse>

    //    모든 카테고리 조회
    @GET("/category")
    fun getRequestCategory(): Call<BasicResponse>

    //    특정 대분류 카테고리(내부의 소분류) 조회
    @GET("/category/{large_category_id}")
    fun getRequestSmallCategoryDependOnLarge(
        @Path("large_category_id") id: Int,
    ): Call<BasicResponse>

    //    모든 소분류 카테고리 조회
    @GET("/category/small")
    fun getRequestCategorySmall(): Call<BasicResponse>

    //   특정 소분류 카테고리(내부의 상품목록) 조회
    @GET("/category/small/{small_category_id}")
    fun getRequestSmallCategorysItemList(
        @Path("small_category_id") id: Int,
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
        @Path("review_id") reviewId: Int,
    ): Call<BasicResponse>

    //      특정 상품 상세보기
    @GET("/product/{product_id}")
    fun getRequestProductDetail(
        @Path("product_id") reviewId: Int,
    ): Call<BasicResponse>

    //   특정 소분류 카테고리(내부의 상품목록) 의 리뷰 조회
    @GET("/category/small/{small_category_id}/review")
    fun getRequestSmallCategorysItemReviewList(
        @Path("small_category_id") id: Int,
    ): Call<BasicResponse>



//    특정 리뷰의 댓글 모아보기
    @GET("review/{review_id}/reply")
    fun getRequestReviewReply(
        @Path("review_id") reveiwReply: Int,
    ): Call<BasicResponse>

    //    리뷰에 댓글 작성
    @POST("review/{review_id}/reply")
    fun postRequestReviewReply(
        @Path("review_id") reviewReply: Int,
        @Field("content") content: String,
    ): Call<BasicResponse>


//    회원정보 수정 - 이메일

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditMyEmail(
        @Field("field") field: String,
        @Field("value") value: String,


        ): Call<BasicResponse>

    //     회원정보 수정 - 전화번호
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditMyNumber(
        @Field("field") field: String,
        @Field("value") value: String,

    ) : Call<BasicResponse>


//    회원정보 수정- 비밀번호
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditMyPassword(
        @Field("field") field: String,
        @Field("value") value: String,
        @Field("current_password") password: String,


        ): Call<BasicResponse>


//    회원정보 수정- 이름
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditMyName(
        @Field("field") field: String,
        @Field("value") value: String,

        ) : Call<BasicResponse>

//      회원 아이디 찾기
    @GET("/user/find/email")
    fun getRequestEmail(
    @Query("nick_name") name: String,
    @Query("phone") phone: String,
    ) : Call<BasicResponse>

//    회원 비밀번호 찾기
    @FormUrlEncoded
    @POST("/user/find/password")
    fun postRequestPassword(
    @Field("email") email: String,
    @Field("nick_name") name: String,
    ) : Call<BasicResponse>

//    장바구니 조회
    @GET("/cart")
    fun getRequestBasketList() : Call<BasicResponse>

    //   장바구니에 상품 등록
    @FormUrlEncoded
    @POST("/cart")
    fun postRequestAddItemToCart(
        @Field("product_id") productId: Int,
    ): Call<BasicResponse>

    //    장바구니 상품 등록
    @FormUrlEncoded
    @POST("/cart")
    fun postRequestProductRegister(
        @Field("product_id") id: Int
    ): Call<BasicResponse>

    //    장바구니 상품 삭제
    @DELETE("/cart")
    fun deleteRequestProduct(
        @Query("product_id") id: Int
    )

//    사용자 작성 리뷰목록
    @GET("/user/review")
    fun getRequestUserReviewList()
    : Call<BasicResponse>


//   사용자 구독상품 결재 목록
    @GET("/user/payment")
    fun getRequestUserPurchaseList()
        : Call<BasicResponse>

}