package com.zip.lock.screen.wallpapers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zip.lock.screen.wallpapers.App
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkUtils @Inject constructor(@ApplicationContext context: Context) {

    private val handler = Handler(Looper.getMainLooper())
    private var lostRunnable: Runnable? = null

    private var isListening = false

    private val _network = MutableLiveData<Boolean>()
    val network: LiveData<Boolean> = _network

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val isWifi = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
            val isCellular = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            if (isWifi || isCellular) {
                lostRunnable?.let {
                    handler.removeCallbacks(it)
                    lostRunnable = null
                }
                _network.postValue(true)
            }
        }

        override fun onLost(network: Network) {
            lostRunnable = Runnable {
                if (!isNetworkAvailable()) {
                    _network.postValue(false)
                }
            }
            handler.postDelayed(lostRunnable!!, 2000)
        }
    }

    fun startListening() {
        if (!isListening) {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            connectivityManager.registerNetworkCallback(request, networkCallback)
            isListening = true
        }
    }

    fun stopListening() {
        if (isNetworkAvailable()) return
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    companion object {
        const val TAG = "NetworkUtils"
    }
}