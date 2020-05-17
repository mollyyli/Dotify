//package com.mollyyli.dotify.activity
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.mollyyli.dotify.ApiManager
//import com.mollyyli.dotify.R
//
//class HttpFetchActivity: AppCompatActivity()  {
//    private lateinit var apiManager: ApiManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_http_fetch)
//
//        apiManager = (application as CoolHttpApp).apiManager
//
//        btnFetch.setOnClickListener {
//            fetchEmailWithRetroFit()
//
//            Log.i(TAG, "update view 1")
//        }
//    }
//
//    private fun fetchEmailWithRetroFit() {
//        apiManager.getEmail ({ email ->
//            Log.i(TAG, "Email received from $email")
//        })
//
//        Log.i(TAG, "update view 1")
//        Log.i(TAG, "update view 2")
//        Log.i(TAG, "update view 3")
//        Log.i(TAG, "update view 4")
//        Log.i(TAG, "update view 5")
//        Log.i(TAG, "update view 6")
//    }
//}