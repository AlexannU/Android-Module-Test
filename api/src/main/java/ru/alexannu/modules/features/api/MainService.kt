package ru.alexannu.modules.features.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MainService {
    @Multipart
    @POST("http://185.189.12.30:3000/post")
    suspend fun uploadFile(
        @Part image: MultipartBody.Part
    ): Response<ResponseBody>
}

fun MainService(): MainService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://185.189.12.30:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(MainService::class.java)
}