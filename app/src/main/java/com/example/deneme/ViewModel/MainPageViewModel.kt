package com.example.deneme.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deneme.Entity.Banks
import com.example.deneme.Repository.BanksDaoRepository

class MainPageViewModel :ViewModel(){
    var bankRepo = BanksDaoRepository()
    var listOfBanks = MutableLiveData<List<Banks>>()

    //https://raw.githubusercontent.com/fatiha380/mockjson/main/bankdata
    init {
        loadBank()
        listOfBanks = bankRepo.getBanks()
    }

    fun loadBank(){
        bankRepo.getAllBank()
    }
    fun search(searchword:String){
        bankRepo.searchBank(searchword)
    }
}