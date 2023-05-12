package com.example.trash1.domain.repository

import com.example.trash1.data.Item
import com.example.trash1.data.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceAPI {
    @GET("search/users?per_page=30")
    fun getLoginUser(
        @Query("q") userSearch: String,
        @Query("page") pageNumber: Int,
    ): Call<Users<Item>>
}