package com.example.deneme.Repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import com.example.deneme.Entity.BankResponse
import com.example.deneme.Entity.Banks
import com.example.deneme.Retrofit.ApiUtils
import com.example.deneme.Retrofit.BanksDaoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BanksDaoRepository {
    var listOfBanks = MutableLiveData<List<Banks>>()
    var banksDaoInterface:BanksDaoInterface

    init {
        banksDaoInterface = ApiUtils.getBanksDaoInterface()
        listOfBanks = MutableLiveData()
    }

    fun getBanks():MutableLiveData<List<Banks>>{
        return listOfBanks
    }

    fun getAllBank(){
        banksDaoInterface.allBanks().enqueue(object : Callback<BankResponse>{
            override fun onResponse(call: Call<BankResponse>, response: Response<BankResponse>) {
                val liste = response.body()?.banks
                listOfBanks.value = liste
            }

            override fun onFailure(call: Call<BankResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun searchBank(searchword:String){
        val list = listOfBanks.value
        val blist = mutableListOf<Banks>()

        if (searchword.equals(""))
        {
            getAllBank()
        }else {
            for (obj in list!!) {
                if (obj.dcSEHIR.contains(searchword)) {
                    blist.add(obj)

                }
            }
            listOfBanks.value = blist
        }
    }
}