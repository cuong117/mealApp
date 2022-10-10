package com.example.themeal.base

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.themeal.R
import com.example.themeal.util.Dialog

abstract class BaseActivity<T : ViewBinding>(private val inflate: Inflate<T>) :
    AppCompatActivity() {

    protected val loadingDialog by lazy {
        Dialog(AlertDialog.Builder(this))
    }
    private val notNetworkDialog by lazy {
        Dialog(AlertDialog.Builder(this))
    }
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
        setUpNetwork()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setUpNetwork() {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                notNetworkDialog.dismiss()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                runOnUiThread {
                    notNetworkDialog.showDisconnectNetworkDialog(
                        getString(R.string.title_disconnect_network),
                        getString(R.string.msg_disconnect_network)
                    )
                }
            }
        }
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}
