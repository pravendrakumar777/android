package com.rcm.engineering.rcm.model

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("id") val id: Long? = null,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("email") var email: String,
    @SerializedName("mobile") var mobile: String,
    @SerializedName("gender") var gender: String
)