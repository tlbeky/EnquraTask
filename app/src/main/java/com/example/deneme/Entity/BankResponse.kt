package com.example.deneme.Entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankResponse(@SerializedName("content")
                        @Expose
                        var banks:List<Banks>) {
}