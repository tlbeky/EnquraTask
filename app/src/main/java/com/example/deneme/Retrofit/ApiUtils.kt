package com.example.deneme.Retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "https://raw.githubusercontent.com/"

        fun getBanksDaoInterface():BanksDaoInterface{
            return RetrofitClient.getClient(BASE_URL).create(BanksDaoInterface::class.java)
        }
    }
}