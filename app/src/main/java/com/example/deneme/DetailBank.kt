package com.example.deneme

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.deneme.Entity.Banks

@Composable
fun DetailBank(bank: Banks) {

    LaunchedEffect(key1 = true ){
        Log.e("Banks","${bank.ID} - ${bank.dcBANKASUBE}")
    }
}