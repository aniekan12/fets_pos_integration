package com.example.fets_pos_integration.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.fets_pos_integration.ICallback

var iCallback: ICallback? = null

class FetsCheckoutBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent) {
        if(iCallback != null){
            iCallback?.callBack(intent.extras?.get("data"))
        } else {
            Log.d("RESPONSE", "${intent.extras?.get("data")}")
        }
    }
}