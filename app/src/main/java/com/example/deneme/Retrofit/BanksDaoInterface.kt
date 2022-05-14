package com.example.deneme.Retrofit

import com.example.deneme.Entity.BankResponse
import retrofit2.Call
import retrofit2.http.GET

interface BanksDaoInterface {
    @GET("tlbeky/Json/main/jsonviewer.json")
    fun allBanks():Call<BankResponse>

}