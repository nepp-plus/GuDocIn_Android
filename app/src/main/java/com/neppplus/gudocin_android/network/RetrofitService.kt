package com.neppplus.gudocin_android.network

import com.neppplus.gudocin_android.model.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    // 로그인 기능
    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") pw: String,
    ): Call<BasicResponse>

    // 회원 가입 기능
    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nick: String,
        @Field("phone") phone: String,
    ): Call<BasicResponse>

    // 중복 확인 기능
    @GET("/user/check")
    fun getRequestDuplicateCheck(
        @Query("type") type: String,
        @Query("value") value: String,
    ): Call<BasicResponse>

    // 소셜 로그인 기능
    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(
        @Field("provider") provider: String,
        @Field("uid") uid: String,
        @Field("nick_name") name: String,
    ): Call<BasicResponse>

    // 내 정보 조회
    @GET("/user")
    fun getRequestInfo(): Call<BasicResponse>

    // 상품 목록 조회
    @GET("/product")
    fun getRequestProduct(): Call<BasicResponse>

    // 리뷰 등록 - Multipart 파일 같이 첨부
    @Multipart
    @POST("/review")
    fun postRequestReview(
        @PartMap params: HashMap<String, RequestBody>,
        @Part img: MultipartBody.Part
    ): Call<BasicResponse>

    // 특정 대분류 카테고리 (내부 소분류) 조회
    @GET("/category/{large_category_id}")
    fun getRequestLargeCategory(
        @Path("large_category_id") id: Int,
    ): Call<BasicResponse>

    // 특정 소분류 카테고리 (내부 상품 목록) 조회
    @GET("/category/small/{small_category_id}")
    fun getRequestSmallCategory(
        @Path("small_category_id") id: Int,
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestUpdateUser(
        @Field("field") field: String,
        @Field("value") value: String,
    ): Call<BasicResponse>

    // 배너 조회
    @GET("/main/banner")
    fun getRequestBanner(): Call<BasicResponse>

    // 특정 리뷰 상세 조회
    @GET("/review/{review_id}")
    fun getRequestDetailReview(
        @Path("review_id") reviewId: Int,
    ): Call<BasicResponse>

    // 특정 상품 상세 조회
    @GET("/product/{product_id}")
    fun getRequestDetailProduct(
        @Path("product_id") reviewId: Int,
    ): Call<BasicResponse>

    // 특정 소분류 카테고리 (내부 상품 목록) 리뷰 조회
    @GET("/category/small/{small_category_id}/review")
    fun getRequestSmallCategoryReview(
        @Path("small_category_id") id: Int,
    ): Call<BasicResponse>

    // 특정 리뷰 댓글 모음
    @GET("review/{review_id}/reply")
    fun getRequestReply(
        @Path("review_id") reviewReply: Int,
    ): Call<BasicResponse>

    // 리뷰 댓글 작성
    @FormUrlEncoded
    @POST("review/{review_id}/reply")
    fun postRequestReply(
        @Path("review_id") reviewReply: Int,
        @Field("content") content: String,
    ): Call<BasicResponse>

    // 회원 정보 수정 - 전화번호
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestPhoneNumber(
        @Field("field") field: String,
        @Field("value") value: String,
    ): Call<BasicResponse>

    // 회원 정보 수정 - 비밀번호
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestPassword(
        @Field("field") field: String,
        @Field("value") value: String,
        @Field("current_password") password: String,
    ): Call<BasicResponse>

    // 회원 정보 수정 - 닉네임
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestNickname(
        @Field("field") field: String,
        @Field("value") value: String,
    ): Call<BasicResponse>

    // 장바구니 내역 조회
    @GET("/cart")
    fun getRequestCart(): Call<BasicResponse>

    // 장바구니 상품 등록
    @FormUrlEncoded
    @POST("/cart")
    fun postRequestCart(
        @Field("product_id") productId: Int,
    ): Call<BasicResponse>

    // 장바구니 상품 삭제
    @DELETE("/cart")
    fun deleteRequestCart(
        @Query("product_id") id: Int
    ): Call<BasicResponse>

    // 사용자 작성 리뷰 목록
    @GET("/user/review")
    fun getRequestUserReview(): Call<BasicResponse>

    // 사용자 구독 상품 목록
    @GET("/user/payment")
    fun getRequestUserPayment(): Call<BasicResponse>

    // 프로필 사진 첨부 - Field 대신 Multipart 활용
    @Multipart
    @PUT("/user/image")
    fun putRequestProfile(
        @Part img: MultipartBody.Part
    ): Call<BasicResponse>
}