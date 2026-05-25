package com.example.metodosnumericos.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: MetodosApi by lazy {

        Retrofit.Builder()
            .baseUrl("https://metodosnumericosumg.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetodosApi::class.java)
    }
}