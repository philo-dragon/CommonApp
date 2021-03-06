package com.pfl.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.pfl.common.netstate.NetType
import com.pfl.common.netstate.NetWork
import com.pfl.common.constant.Constants
import com.pfl.common.http.*
import com.pfl.common.netstate.NetworkManager
import com.pfl.demo.DemoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetworkManager.getInstance().registerObserver(this)
        requestData()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameParent, DemoFragment())
            .commit()
    }

    private fun requestData() {
        NetUtil.request(
            null,
            "https:xxxxx",
            WanAndroidBean::class.java
        ) { tvText.text = it.toString() }
    }


    @NetWork(netType = NetType.WIFI)
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
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkManager.getInstance().unRegisterObserver(this)
    }
}
