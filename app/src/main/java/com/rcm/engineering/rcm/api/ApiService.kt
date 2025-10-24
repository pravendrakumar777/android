package com.rcm.engineering.rcm.api

import com.rcm.engineering.rcm.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/users/list")
    suspend fun fetchAllUsers(): Response<List<User>>
}