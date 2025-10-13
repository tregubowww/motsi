package com.example.motsi.core.network.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import javax.inject.Inject

//class NetworkHelper{
//    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
//    fun isInternetAvailable(context: Context): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val network = connectivityManager.activeNetwork ?: return false
//            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
//
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
//                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
//                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
//        } else {
//            @Suppress("DEPRECATION")
//            val networkInfo = connectivityManager.activeNetworkInfo
//            networkInfo != null && networkInfo.isConnected
//        }
//    }
//}
class NetworkHelper @Inject constructor() {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            // Проверяем, что сеть имеет доступ к интернету и прошла валидацию
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                    (
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) // Учитываем VPN
                            )
        } else {
            @SuppressLint("Deprecation")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }
}