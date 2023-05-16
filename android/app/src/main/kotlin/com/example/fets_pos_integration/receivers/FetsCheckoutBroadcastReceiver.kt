package com.example.fets_pos_integration.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.fets_pos_integration.interfaces.IFetsCallback

var iFetsCallback: IFetsCallback? = null


class FetsCheckoutBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent) {
        if(iFetsCallback != null){
            iFetsCallback?.callBack(intent.extras?.get("data"))
        }
    }
}