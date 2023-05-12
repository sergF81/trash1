package com.example.trash1.data

import com.google.gson.annotations.SerializedName

data class Users<Item>(
    @SerializedName("items")

    val items: List<Item>

    )

    data class Item(
        @SerializedName("login")

        val loginUser: String,
        val id: Int,
        @SerializedName("avatar_url")
        val avatarUrl: String

    )