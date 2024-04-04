package com.example.receipts.service

import android.util.Log
import com.example.receipts.model.Hit
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetrofitService {
    @GET("v2?type=public&q=all&app_id=d9c85a5b&app_key=d2077f185a61e257e52864c148aec718&random=false")
    suspend fun getAllRecipes(): Response<Hit>

    companion object {
        private var retrofitService: RetrofitService? = null
        const val BASE_URL: String = "https://api.edamam.com/api/recipes/"
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                Log.e("hkklk", "yhjhj")
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}

