package com.example.deneme

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.deneme.Entity.Banks
import com.example.deneme.ViewModel.MainPageViewModel
import com.example.deneme.ui.theme.DenemeTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DenemeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationPage()
                }
            }
        }
    }
}
@Composable
fun NavigationPage(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa" ){
        composable("anasayfa"){
            MainPage(navController = navController)
        }
        composable("detay/{bank}", arguments = listOf(
            navArgument("bank"){type = NavType.StringType}
        )){
            val nesne = it.arguments?.getString("bank")
            val json = Gson().fromJson(nesne, Banks::class.java)
            DetailBank(bank = json)
        }
    }
}


@Composable
fun MainPage(navController: NavController) {
    val isSearch = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }
    val viewModel:MainPageViewModel = viewModel()
    val listOfBanks = viewModel.listOfBanks.observeAsState(listOf())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearch.value){
                        TextField(
                            value = tf.value,
                            onValueChange = {
                                tf.value = it
                                viewModel.search(it)
                            },
                            label = { Text(text = "Search...")},
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                unfocusedIndicatorColor = Color.White
                            )
                        )
                    }else{
                        Text(text = "Arit Banks")
                    }
                },
                actions = {
                    if (isSearch.value){
                        IconButton(onClick = {
                            isSearch.value = false
                            tf.value = ""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.close_icon), contentDescription ="",
                            tint = Color.White)
                        }
                    }else{
                        IconButton(onClick = {
                            isSearch.value = true
                            tf.value = ""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.search_icon), contentDescription ="",
                                tint = Color.White)
                        }
                    }
                }
            )
        },
        content = {
            LazyColumn{
                items(
                    count = listOfBanks.value!!.count(),
                    itemContent = {
                        val bank = listOfBanks.value!![it]
                        Card(modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxWidth()) {
                            Row(modifier = Modifier.clickable {
                                //NavigationPage fonksiyonun da start destination olarak belirlenen anasayfa yı görmüyor hatası veriliyor.
                                //Hata sebebiyle kapatılmıştır.

                               // val json = Gson().toJson(bank)
                                //navController.navigate("detay/${json}")
                            }) {
                                Row(
                                    modifier = Modifier
                                        .padding(all = 10.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text ="${bank.ID} - ${bank.dcSEHIR}   -> ${bank.dcADRESADI}")
                                }
                            }
                        }
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DenemeTheme {

    }
}