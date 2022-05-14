package com.example.deneme

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    @RequiresApi(Build.VERSION_CODES.M)
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
@RequiresApi(Build.VERSION_CODES.M)
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


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun MainPage(navController: NavController) {
    val context = LocalContext.current
    val isSearch = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }
    val viewModel:MainPageViewModel = viewModel()
    val listOfBanks = viewModel.listOfBanks.observeAsState(listOf())

    val online = isOnline(context)

    if (online)
    {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (isSearch.value){
                            TextField(
                                value = tf.value,
                                onValueChange = {
                                    tf.value = it
                                    var text = tf.value.uppercase()
                                    viewModel.search(text)
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
                            viewModel.loadBank()
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
    }else{
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (isSearch.value){
                            TextField(
                                value = tf.value,
                                onValueChange = {
                                    tf.value = it
                                    var text = tf.value.uppercase()
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
                Text(text = "No Internet Connection")
            }
        )
    }




}

@RequiresApi(Build.VERSION_CODES.M)
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DenemeTheme {

    }
}