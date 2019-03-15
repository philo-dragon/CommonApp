package com.pfl.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.pfl.common.netstate.NetType
import com.pfl.common.netstate.NetWork
import com.pfl.common.constant.Constants
import com.pfl.common.http.*
import com.pfl.common.netstate.NetworkManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetworkManager.getInstance().registerObserver(this)

        requestData()
    }

    private fun requestData() {
        NetUtil.request(
            null,
            "https://wanandroid.com/wxarticle/chapters/json",
            WanAndroidBean::class.java
        ) { tvText.text = it.toString() }
    }


    @NetWork(netType = NetType.AUTO)
    fun network(netType: NetType) {

        when (netType) {
            NetType.WIFI -> {
                Log.e(Constants.TAG, " NetType.WIFI  ===== $netType")
                Toast.makeText(this@MainActivity, " NetType.WIFI  ===== $netType", Toast.LENGTH_SHORT).show()
            }
            NetType.CMNET -> {
                Log.e(Constants.TAG, " NetType.CMNET  ===== $netType")
                Toast.makeText(this@MainActivity, " NetType.CMNET  ===== $netType", Toast.LENGTH_SHORT).show()
            }
            NetType.CMWAP -> {
                Log.e(Constants.TAG, " NetType.CMWAP  ===== $netType")
                Toast.makeText(this@MainActivity, " NetType.CMWAP  ===== $netType", Toast.LENGTH_SHORT).show()
            }
            NetType.NONE -> {
                Log.e(Constants.TAG, " NetType.NONE  ===== $netType")
                Toast.makeText(this@MainActivity, " NetType.CMWAP  ===== $netType", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkManager.getInstance().unRegisterObserver(this)
    }
}
